import parser.MetroLine;
import parser.MetroStation;
import parser.ParsingPages;
import search.SearchFilesCSV;
import search.SearchFilesJSON;
import storage.LinesData;
import storage.StationData;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String htmlFile = ParsingPages.parseFile("data/code.html");
        // System.out.println(htmlFile);

        // Парсинг страницы и поиск линий и станций метро
        List<MetroLine> metroLines = MetroLine.parseMetroLines(htmlFile);
        List<MetroStation> metroStations = MetroStation.parseMetroStations(htmlFile);

        // Вывод найденных линий
        System.out.println("Найденные линии метро:");
        for (MetroLine line : metroLines) {
            System.out.println("Линия: " + line.getNameLine() + ", Номер: " + line.getNumLine());
        }

        // Вывод найденных станций
        System.out.println("\nНайденные станции метро:");
        for (MetroStation station : metroStations) {
            System.out.println("Станция: " + station.getNameStation() + ", Номер линии: " + station.getNumStation() +
                    ", Наличие перехода: " + station.isHasConnection());
        }

        // Поиск файлов в указанной директории
        String directoryPath = "E:\\Skillbox\\FilesAndNetwork\\DataCollector\\stations-data";
        List<File> foundFilesJSON = SearchFilesJSON.searchJSONFiles(directoryPath);
        List<File> foundFilesCSV = SearchFilesCSV.searchCSVFiles(directoryPath);

        System.out.println("\nНайденные файлы CSV: ");
        for (File file : foundFilesCSV) {
            System.out.println(file.getAbsolutePath());
        }
        // Вывод найденных файлов JSON
        System.out.println("\nНайденные файлы JSON :");
        for (File file : foundFilesJSON) {
            System.out.println(file.getAbsolutePath());
        }

        List<StationData> stationDataList = StationData.loadDataFromAllJSONFilesInDirectory(directoryPath);
        //Выводим информацию о каждом объекте StationData (JSON файлы)
        for (StationData data : stationDataList) {
            System.out.println("Station name: " + data.getStation_name());
            System.out.println("Depth: " + data.getDepth());
            System.out.println();
        }
        List<File> csvFiles = SearchFilesCSV.searchCSVFiles(directoryPath);

        // Создаем список для хранения данных из CSV файлов
        List<LinesData> allData = new ArrayList<>();

        // Загружаем данные из каждого CSV файла и добавляем их в общий список
        for (File file : csvFiles) {
            List<LinesData> dataFromCSV = LinesData.loadDataFromCSV(file);
            allData.addAll(dataFromCSV);
        }

        // Выводим данные
        for (LinesData data : allData) {
            System.out.println(data);
        }
    }
}





