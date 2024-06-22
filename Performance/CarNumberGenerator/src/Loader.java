import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Loader {

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();

        ExecutorService service = Executors.newFixedThreadPool(4);

        service.execute(() -> {
            try {
                generateNumbers(1, 50, "res/n1.txt");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        service.execute(() -> {
            try {
                generateNumbers(51, 100, "res/n2.txt");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        service.execute(() -> {
            try {
                generateNumbers(101, 150, "res/n3.txt");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        service.execute(() -> {
            try {
                generateNumbers(151, 200, "res/n4.txt");
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
        try (BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(fileName))) {
            char letters[] = {'У', 'К', 'Е', 'Н', 'Х', 'В', 'А', 'Р', 'О', 'С', 'М', 'Т'};
            StringBuilder sb = new StringBuilder();
            for (int number = 1; number < 1000; number++) {
                for (int regionCode = regionStart; regionCode <= regionEnd; regionCode++) {
                    for (char firstLetter : letters) {
                        for (char secondLetter : letters) {
                            for (char thirdLetter : letters) {
                                sb.append(firstLetter)
                                        .append(padNumber(number, 3))
                                        .append(secondLetter)
                                        .append(thirdLetter)
                                        .append(padNumber(regionCode, 2))
                                        .append("\n");
                            }
                        }
                    }
                    writer.write(sb.toString().getBytes());
                    sb.setLength(0);
                }
            }
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

