import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import java.util.Scanner;

public class Main {
    private static final String ADD_COMMAND = "add Василий Петров " +
            "vasily.petrov@gmail.com +79215637722";
    private static final String EXIT_COMMAND = "\texit";
    private static final String COMMAND_EXAMPLES = "\t" + ADD_COMMAND + "\n" +
            "\tlist\n\tcount\n\tremove Василий Петров\n" + EXIT_COMMAND;
    private static final String COMMAND_ERROR = "Wrong command! Available command examples: \n" + COMMAND_EXAMPLES;
    private static final String helpText = "Command examples:\n" + COMMAND_EXAMPLES;

    private static final Logger LOGGER = LogManager.getLogger(Main.class);
    private static final Marker INPUT_HISTORY_MARKER = MarkerManager.getMarker("INPUT_HISTORY");
    private static final Marker INVALID_STATIONS_MARKER = MarkerManager.getMarker("INVALID_STATIONS");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CustomerStorage executor = new CustomerStorage();

        while (true) {
            String command = scanner.nextLine();
            String[] tokens = command.split("\\s+", 2);

            switch (tokens[0]) {
                case "add":
                    try {
                        executor.addCustomer(tokens[1]);
                        LOGGER.info(INPUT_HISTORY_MARKER, "Добавлен новый клиент");
                    } catch (IncorrectInputStringException | IncorrectPhoneNumberException |
                             IncorrectEmailException e) {
                        LOGGER.error(INVALID_STATIONS_MARKER, "Не удалось добавить нового клиента: {}",
                                e.getMessage());
                        System.out.println(e.getMessage());
                    }
                    break;
                case "list":
                    executor.listCustomers();
                    LOGGER.info(INPUT_HISTORY_MARKER, "Список клиентов: ");
                    break;
                case "remove":
                    executor.removeCustomer(tokens[1]);
                    LOGGER.info(INPUT_HISTORY_MARKER, "Удаленный клиент: ");
                    break;
                case "count":
                    System.out.println("Количество клиентов: " + executor.getCount());
                    LOGGER.info(INPUT_HISTORY_MARKER, "Количество клиентов: ");
                    break;
                case "help":
                    System.out.println(helpText);
                    LOGGER.info(INPUT_HISTORY_MARKER, "Справочная информация: ");
                    break;
                case "exit":
                    System.out.println("Выход...");
                    return;
                default:
                    System.out.println(COMMAND_ERROR);
                    LOGGER.warn(INVALID_STATIONS_MARKER, "Неизвестная команда: {}", command);
                    break;
            }
        }
    }
}
