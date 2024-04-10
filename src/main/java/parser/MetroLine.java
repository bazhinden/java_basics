package parser;

import lombok.Data;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

@Data
public class MetroLine {
    private String nameLine;
    private String numLine;


    public MetroLine(String name, String numberLine) {
        this.nameLine = name;
        this.numLine = numberLine;
    }

    // Метод для парсинга информации о линиях метро
    public static List<MetroLine> parseMetroLines(String htmlFile) {
        List<MetroLine> metroLines = new ArrayList<>();
        Document doc = Jsoup.parse(htmlFile);
        Elements elements = doc.select(".js-metro-line");
        for (Element element : elements) {
            String lineName = element.text();
            String numberLine = element.attr("data-line");
            metroLines.add(new MetroLine(lineName, numberLine));
        }
        return metroLines;
    }
}
