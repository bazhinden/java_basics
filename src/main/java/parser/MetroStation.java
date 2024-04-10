package parser;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class MetroStation {
    private String nameStation;
    private String numStation;
    private boolean hasConnection;

    // Метод для парсинга информации о станциях метро
    public static List<MetroStation> parseMetroStations(String htmlFile) {
        List<MetroStation> metroStations = new ArrayList<>();
        Document doc = Jsoup.parse(htmlFile);
        Elements elements = doc.select(".single-station");
        for (Element element : elements) {
            String stationName = element.select(".name").text().trim();
            String stationNumber = element.select(".num").text().trim();
            boolean stationTransfer = element.select(".t-icon-metroln").size() > 0;
            metroStations.add(new MetroStation(stationName, stationNumber,stationTransfer));
        }
        return metroStations;
    }
}
