package ParseJson;

import SearchFilesInFolders.SearchFiles;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ParseJsonFiles {
    public List<DepthStation> getDepthStations() {
        SearchFiles searchFiles = new SearchFiles();
        List<DepthStation> depthStations = new ArrayList<>();
        try {
            List<File> jsonFiles = searchFiles.getJson();
            for (File json : jsonFiles) {
                String jsonStr = Files.readString(Paths.get(json.getPath()));
                JSONArray array = new JSONArray(jsonStr);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    depthStations.add(new DepthStation(object.getString("station_name"), object.getString("depth")));
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return depthStations;
    }
}