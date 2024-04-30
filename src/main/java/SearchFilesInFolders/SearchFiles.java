package SearchFilesInFolders;

import CollectAllData.Main;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SearchFiles {
    private final File directory;
    public SearchFiles(){
        this.directory  = new File(Main.PATH);
    }

    private List<File> getFileList(File directory){
        List<File> fileList = new ArrayList<>();
        File[] files = directory.listFiles();
        if(files != null){
            for (File file : files) {
                if(file.isDirectory()){
                    fileList.addAll(getFileList(file));
                }else {
                    fileList.add(file);
                }
            }
        }

        return fileList;
    }
    public List<File> getJson(){
        List<File> allFiles = getFileList(directory);
        List<File> jonFiles = new ArrayList<>();
        for (File file : allFiles) {
            if(file.getName().toLowerCase().endsWith(".json")){
                jonFiles.add(file);
            }
        }
        return jonFiles;
    }
    public List<File> getCSV(){
        List<File> allFiles = getFileList(directory);
        List<File> csvFiles = new ArrayList<>();
        for (File file : allFiles) {
            if(file.getName().toLowerCase().endsWith(".csv")){
                csvFiles.add(file);
            }
        }
        return csvFiles;
    }
}