import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Loader {

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();

        int numThreads = 199;
        ExecutorService service = Executors.newFixedThreadPool(numThreads);


        for (int regionCode = 1; regionCode <= 199; regionCode++) {
            final int region = regionCode;
            service.execute(() -> {
                try {
                    generateNumbers(region, "res/" + region + ".txt");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        service.shutdown();
        service.awaitTermination(1, TimeUnit.HOURS);
        
        System.out.println((System.currentTimeMillis() - start) + " ms");
    }

    public static void generateNumbers(int regionCode, String fileName) throws IOException {
        try (BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(fileName))) {
            char letters[] = {'У', 'К', 'Е', 'Н', 'Х', 'В', 'А', 'Р', 'О', 'С', 'М', 'Т'};
            StringBuilder sb = new StringBuilder();
            for (int number = 1; number < 1000; number++) {
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
            }
            writer.write(sb.toString().getBytes());
            sb.setLength(0);
        }
    }

    private static String padNumber(int number, int numberLength) {
        String numberStr = Integer.toString(number);
        while (numberStr.length() < numberLength) {
            numberStr = '0' + numberStr;
        }
        return numberStr;
    }
}

