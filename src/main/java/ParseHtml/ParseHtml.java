package ParseHtml;
import CollectAllData.Main;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class ParseHtml {

    public Document getDocHtml() throws IOException {
        return Jsoup.connect(Main.URL).get();
    }

    public List<Line> parseLine() {
        List<Line> linesList = new ArrayList<>();
        try {
            Element element = getDocHtml();
            for (Element e : element.select("span.js-metro-line")) {
                Line line = new Line(e.attr("data-line"), e.text());
                linesList.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return linesList;
    }

    public LinkedHashMap<String, ArrayList<String>> parseStation() {
        LinkedHashMap<String, ArrayList<String>> stationsMap = new LinkedHashMap<>();
        ArrayList<ArrayList<String>> stations = new ArrayList<>();
        ArrayList<String> numsOfLines = new ArrayList<>();
        try {
            Element element = getDocHtml();
            for (Element e : element.select("div.js-depend > div.js-metro-stations ")) {
                String s = e.text();
                String newStr = s.replaceAll("[0-9]", "");
                String[] arr = newStr.split("\\.");
                List<String> list1 = Arrays.stream(arr).distinct().toList();
                List<String> l = new ArrayList<>();
                for (String s1 : list1) {
                    if (s1.isEmpty()) {
                        continue;
                    }
                    l.add(s1.trim());
                }
                stations.add(new ArrayList<>(l));
            }
            for (Element e : element.select("span.js-metro-line")) {
                numsOfLines.add(e.attr("data-line"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < numsOfLines.size() && i < stations.size(); i++) {
            stationsMap.put(numsOfLines.get(i), stations.get(i));
        }
        return stationsMap;

    }

    public List<Connections> parseConnection() {
        List<String> fullNameConnection = new ArrayList<>();
        List<String> numbersOfLinesWithSymbols = new ArrayList<>();
        List<Connections> connections = new ArrayList<>();
        try {
            Element element = getDocHtml();
            for (Element el : element.select("p.single-station span.t-icon-metroln")) {
                fullNameConnection.add(el.attr("title"));
                numbersOfLinesWithSymbols.add(el.attr("class"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String regex = "(?<=[\\(\\{\\['\\«]).+(?=[\\}\\]'\\»])";
        Pattern pattern1 = Pattern.compile(regex);
        List<String> shortNameConnection = new ArrayList<>();
        for (String s : fullNameConnection) {
            Matcher matcher = pattern1.matcher(s);
            if (matcher.find()) {
                shortNameConnection.add(matcher.group().trim());
            }
        }
        List<String> numsOfLines = new ArrayList<>();
        for (String number : numbersOfLinesWithSymbols) {
            numsOfLines.add(number.replaceAll("[^0-9]", ""));
        }
        for (int i = 0; i < numsOfLines.size() && i < fullNameConnection.size(); i++) {
            connections.add(new Connections(numsOfLines.get(i), shortNameConnection.get(i)));
        }
        return connections;
    }
}