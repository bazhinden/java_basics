package search;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SearchFilesCSV {
    // Метод для поиска файлов формата CSV в указанной директории
    public static List<File> searchCSVFiles(String directoryPath) {
        List<File> foundFiles = new ArrayList<>();
        searchFilesRecursively(new File(directoryPath), foundFiles);
        return foundFiles;
    }

    // Рекурсивный метод для обхода файлов и папок
    private static void searchFilesRecursively(File directory, List<File> foundFiles) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // Рекурсивно вызываем этот метод для каждой папки
                    searchFilesRecursively(file, foundFiles);
                } else {
                    // Проверяем файл на соответствие формату CSV
                    String fileName = file.getName().toLowerCase();
                    if (fileName.endsWith(".csv")) {
                        foundFiles.add(file);
                    }
                }
            }
        }
    }
}
