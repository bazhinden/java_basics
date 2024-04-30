package CollectAllData;
public class Main {
    public static final String PATH = "stations-data";
    public static final String URL = "https://skillbox-java.github.io/";
    public static void main(String[] args) {
        CollectAllData collectAllData = new CollectAllData();
        collectAllData.writeJson();

    }
}