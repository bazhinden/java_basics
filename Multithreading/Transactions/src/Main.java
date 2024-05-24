
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Bank bank = new Bank();

        bank.getAccounts().put("Account1", new Account(100000));
        bank.getAccounts().put("Account2", new Account(100000));
        bank.getAccounts().put("Account3", new Account(100000));
        bank.getAccounts().put("Account4", new Account(100000));

        long sumBeforeTransfer = bank.getSumAllAccounts();
        System.out.println("Сумма до переводов: " + sumBeforeTransfer);

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 10_000; i++) {
                bank.transfer("Account1", "Account2", 1000);
                bank.transfer("Account3", "Account4", 1000);
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 10_000; i++) {
                bank.transfer("Account2", "Account1", 1000);
                bank.transfer("Account4", "Account3", 1000);
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        long sumAfterTransfer = bank.getSumAllAccounts();
        System.out.println("Сумма после переводов: " + sumAfterTransfer);

        if (sumBeforeTransfer == sumAfterTransfer) {
            System.out.println("Суммы совпадают. Дедлоков нет.");
        } else {
            System.out.println("Суммы не совпадают. Есть ошибка в логике перевода.");
        }
    }
}