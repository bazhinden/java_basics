import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class Bank {
    private final Map<String, Account> accounts = new ConcurrentHashMap<>();
    private final Random random = new Random();

    public synchronized boolean isFraud(String fromAccountNum, String toAccountNum, long amount)
            throws InterruptedException {
        Thread.sleep(1000);
        return random.nextBoolean();
    }

    public Map<String, Account> getAccounts() {
        return accounts;
    }

    public void transfer(String fromAccountNum, String toAccountNum, long amount) {
        Account fromAccount = accounts.get(fromAccountNum);
        Account toAccount = accounts.get(toAccountNum);
        if (fromAccount == null || toAccount == null) {
            throw new IllegalArgumentException("Недопустимый номер счета(ов)");
        }
        synchronized (fromAccount) {
            synchronized (toAccount) {
                if (amount > 50000) {
                    try {
                        if (isFraud(fromAccountNum, toAccountNum, amount)) {
                            fromAccount.setBlocked(true);
                            toAccount.setBlocked(true);
                            System.out.println("Обнаружены мошеннические действия! Счета заблокированы!");
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
                if (fromAccount.getMoney() >= amount) {
                    fromAccount.setMoney(fromAccount.getMoney() - amount);
                    toAccount.setMoney(toAccount.getMoney() + amount);
                    System.out.println("Транзакция успешна: " + amount + " переведено с " +
                            fromAccountNum + " на " + toAccountNum);
                } else {
                    System.out.println("Не достаточно средств на счёте отправителя.");
                }
            }
        }

    }

    public long getBalance(String accountNum) {
        Account account = accounts.get(accountNum);
        if (account != null) {
            return account.getMoney();
        }
        throw new IllegalArgumentException("Недопустимый номер счёта");
    }

    public long getSumAllAccounts() {
        return accounts.values().stream()
                .mapToLong(Account::getMoney)
                .sum();
    }
}
