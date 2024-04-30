package CollectAllData;

import ParceCsv.DatesStationsOpened;
import ParceCsv.ParseCsv;
import ParseHtml.Connections;
import ParseHtml.ParseHtml;
import ParseHtml.Line;
import ParseJson.DepthStation;
import ParseJson.ParseJsonFiles;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class CollectAllData {
    private final ParseCsv parseCsv;
    private final ParseJsonFiles parseJsonFiles;
    private final ParseHtml parseHtml;

    public CollectAllData() {
        this.parseCsv = new ParseCsv();
        this.parseJsonFiles = new ParseJsonFiles();
        this.parseHtml = new ParseHtml();

    }

    public List<Stations> collectStations(){
        LinkedHashMap<String , ArrayList<String>> stationWithLines = parseHtml.parseStation();
        List<ArrayList<String>> stationsLists = new ArrayList<>();
        stationWithLines.forEach((s, strings) -> stationsLists.add(strings));
        List<Connections> connections = parseHtml.parseConnection();
        List<Line> lines = parseHtml.parseLine();
        List<DatesStationsOpened> datesStat = parseCsv.getDateStations();
        List<DepthStation> depthStations = parseJsonFiles.getDepthStations();
        List<Stations> stationsObj = new ArrayList<>();
        int i = 0;
        for (ArrayList<String> strings : stationsLists) {
            for (String string : strings) {
                stationsObj.add(new Stations(string, lines.get(i).getName(), null, null, false));
            }
            i++;
        }
        for (DatesStationsOpened datesStationsOpened : datesStat) {
            for (Stations value : stationsObj) {
                if (datesStationsOpened.getNameOfStation().equals(value.getName())) {
                    value.setDate(datesStationsOpened.getDateStationOpened());
                }
            }
        }
        for (DepthStation depthStation : depthStations) {
            for (Stations station1 : stationsObj) {
                if(depthStation.getStationName().equals(station1.getName())
                        && !depthStation.getDepth().equals("0") && !depthStation.getDepth().equals("?")){
                    station1.setDepth(depthStation.getDepth());
                }
            }
        }
        for (Connections connection : connections) {
            for (Stations station1 : stationsObj) {
                if(connection.getStation().equals(station1.getName())){
                    station1.setHasConnection(true);
                }
            }
        }
        return stationsObj;
    }

    public void writeJson(){
        try {
            List<Stations> stations = collectStations();
            Map<String, Object> mapStations = new HashMap<>();
            mapStations.put("stations", stations);
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
            ObjectWriter objectWriter = mapper.writer(new DefaultPrettyPrinter());
            objectWriter.writeValue(new File("E:\\Skillbox\\java_basics\\DataCollector\\src\\main\\java\\Json_Finished_Files/JSON_Result_Stations.json"), mapStations);
        } catch (IOException e){
            e.printStackTrace();
        }
        try {
            LinkedHashMap<String, ArrayList<String>> stationWithLines = parseHtml.parseStation();
            List<Line> lines = parseHtml.parseLine();
            Map<String, Object> mapMetro = new HashMap<>();
            mapMetro.put("stations", stationWithLines);
            mapMetro.put("lines", lines);
            ObjectMapper mapper = new ObjectMapper();
            ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
            writer.writeValue(new File("E:\\Skillbox\\java_basics\\DataCollector\\src\\main\\java\\Json_Finished_Files/JSON_Result_MetroData.json"), mapMetro);
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
