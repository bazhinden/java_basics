package storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import search.SearchFilesJSON;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@ToString
public class StationData {
    private String station_name;
    private String depth;

    // Метод для загрузки данных из всех JSON-файлов в указанной директории
    public static List<StationData> loadDataFromAllJSONFilesInDirectory(String directoryPath) {
        List<File> jsonFiles = SearchFilesJSON.searchJSONFiles(directoryPath);
        List<StationData> dataList = new ArrayList<>();

        for (File file : jsonFiles) {
            List<StationData> dataFromJsonFile = loadDataFromJSON(file.toPath());
            dataList.addAll(dataFromJsonFile);
        }

        return dataList;
    }

    // Метод для загрузки данных из JSON-файла
    private static List<StationData> loadDataFromJSON(Path filePath) {
        List<StationData> stationDataList = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = filePath.toFile();
            StationData[] stationDataArray = objectMapper.readValue(file, StationData[].class);
            stationDataList = Arrays.asList(stationDataArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stationDataList;
    }
}
