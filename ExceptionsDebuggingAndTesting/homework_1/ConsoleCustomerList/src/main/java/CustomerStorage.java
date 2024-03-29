import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerStorage {
    private final Map<String, Customer> storage;
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^\\+?[0-9]+$");

    private static final Logger LOGGER = LogManager.getLogger(CustomerStorage.class);
    private static final Marker INVALID_INPUT_MARKER = MarkerManager.getMarker("INVALID_INPUT");
    private static final Marker STORAGE_OPERATION_MARKER = MarkerManager.getMarker("STORAGE_OPERATION");

    public CustomerStorage() {
        storage = new HashMap<>();
    }

    public void addCustomer(String data) throws IllegalArgumentException {
        final int INDEX_NAME = 0;
        final int INDEX_SURNAME = 1;
        final int INDEX_EMAIL = 2;
        final int INDEX_PHONE = 3;

        try {
            String[] components = data.split("\\s+");
            if (components.length != 4) {
                throw new IllegalArgumentException("Неправильное количество компонентов во входных данных");
            }
            String name = components[INDEX_NAME] + " " + components[INDEX_SURNAME];
            if (!validateEmail(components[INDEX_EMAIL])) {
                throw new IllegalArgumentException("Неверный формат электронной почты");
            }

            if (!validatePhone(components[INDEX_PHONE])) {
                throw new IllegalArgumentException("Неверный формат номера телефона");
            }
            storage.put(name, new Customer(name, components[INDEX_PHONE], components[INDEX_EMAIL]));
        } catch (IllegalArgumentException e) {
            LOGGER.error(INVALID_INPUT_MARKER, "Ошибка при добавлении клиента: {}", e.getMessage());
            throw e;
        }
    }

    public void listCustomers() {
        storage.values().forEach(System.out::println);
        LOGGER.info(STORAGE_OPERATION_MARKER, "Список клиентов: ");
    }

    public void removeCustomer(String name) {
        storage.remove(name);
        LOGGER.info(STORAGE_OPERATION_MARKER, "Удаленный клиент: ");
    }

    public Customer getCustomer(String name) {
        Customer customer = storage.get(name);
        if (customer == null) {
            LOGGER.warn(INVALID_INPUT_MARKER, "Клиент с именем \"{}\" не найден", name);
        }
        return customer;
    }

    public int getCount() {
        return storage.size();
    }

    private boolean validateEmail(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }

    private boolean validatePhone(String phone) {
        Matcher matcher = PHONE_PATTERN.matcher(phone);
        return matcher.matches();
    }
}