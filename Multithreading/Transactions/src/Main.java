import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Bank bank = new Bank();
        bank.getAccounts().put("111111", new Account(100000));
        bank.getAccounts().put("222222", new Account(50000));
        ExecutorService executor = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            executor.submit(() ->{
                Account fromAccount = bank.getAccounts().get("111111");
                Account toAccount = bank.getAccounts().get("222222");
                fromAccount.setBlocked(false);
                toAccount.setBlocked(false);

                bank.transfer("111111", "222222", 60000);
                System.out.println(bank.getSumAllAccounts());
                System.out.println(bank.getBalance("222222"));
                System.out.println(bank.getBalance("111111"));
            });
        }
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
        System.out.println("Main ends");
    }
}