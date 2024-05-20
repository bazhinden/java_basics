import java.io.File;

public class Main {
    public static int THREAD_NUMBER = 1;
    public static int NEW_WIDTH = 1600;
    public static String DST_FOLDER = "E:\\Skillbox\\java_basics\\Multithreading\\ImageResizer\\dstImages";
    public static String SRC_FOLDER = "E:\\Skillbox\\java_basics\\Multithreading\\ImageResizer\\sourceImages";
    public static long START = System.currentTimeMillis();
    public static int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) {
        File srcDir = new File(SRC_FOLDER);
        File[] files = srcDir.listFiles();

        if (files == null) {
            System.err.println("Исходная папка пуста или не существует.");
            return;
        }

        File dstDir = new File(DST_FOLDER);
        if (!dstDir.exists()) {
            dstDir.mkdirs();
        }

        int filesPerThread = (int) Math.ceil((double) files.length / AVAILABLE_PROCESSORS);

        for (int i = 0; i < AVAILABLE_PROCESSORS; i++) {
            int start = i * filesPerThread;
            int end = Math.min(start + filesPerThread, files.length);

            if (start < end) {
                File[] filesForThread = new File[end - start];
                System.arraycopy(files, start, filesForThread, 0, end - start);
                resizeMethod(filesForThread);
            }
        }
    }

    public static void resizeMethod(File[] files) {
        ImageResizer imageResizer = new ImageResizer(files, NEW_WIDTH, DST_FOLDER, START, THREAD_NUMBER);
        new Thread(imageResizer).start();
        THREAD_NUMBER++;
    }
}
