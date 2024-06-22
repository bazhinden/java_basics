import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Loader {

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();

        ExecutorService service = Executors.newFixedThreadPool(2);

        service.execute(() -> {
            try {
                generateNumbers(1, 100, "res/n1.txt");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        service.execute(() -> {
            try {
                generateNumbers(101, 200, "res/n2.txt");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        service.shutdown();
        try {
            if (!service.awaitTermination(1, TimeUnit.HOURS)) {
                System.out.println("Не удалось завершить выполнение задач в течении заданного времени.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println((System.currentTimeMillis() - start) + " ms");
    }

    public static void generateNumbers(int regionStart, int regionEnd, String fileName) throws IOException {
        try (FileOutputStream writer = new FileOutputStream(fileName)) {
            char letters[] = {'У', 'К', 'Е', 'Н', 'Х', 'В', 'А', 'Р', 'О', 'С', 'М', 'Т'};
            for (int number = 1; number < 1000; number++) {
                int regionCode = 199;
                for (char firstLetter : letters) {
                    for (char secondLetter : letters) {
                        for (char thirdLetter : letters) {
                            String carNumber = firstLetter + padNumber(number, 3) +
                                    secondLetter + thirdLetter + padNumber(regionCode, 2);
                            writer.write(carNumber.getBytes());
                            writer.write('\n');
                        }
                    }
                }
            }
            writer.flush();
        }
    }

    private static String padNumber(int number, int numberLength) {
        StringBuilder numberStr = new StringBuilder(Integer.toString(number));
        while (numberStr.length() < numberLength) {
            numberStr.insert(0, '0');
        }
        return numberStr.toString();
    }
}

