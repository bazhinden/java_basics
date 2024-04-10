package storage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Setter
@Getter
@ToString
public class LinesData {
    private String lineName;
    private LocalDate date;

    // Метод для загрузки данных из CSV файла
    public static List<LinesData> loadDataFromCSV(File file) {
        List<LinesData> dataList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String stationName = parts[0];
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                LocalDate date = LocalDate.parse(parts[1], formatter);
                dataList.add(new LinesData(stationName, date));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataList;
    }
}
