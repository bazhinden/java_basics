import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SAXVoteHandler extends DefaultHandler {

    private static SimpleDateFormat visitDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
    private static Map<Integer, WorkTime> voteStationWorkTimes = new ConcurrentHashMap<>();
    private static Map<String, Integer> voterCounts = new ConcurrentHashMap<>();

    public Map<Integer, WorkTime> getVoteStationWorkTimes() {
        return voteStationWorkTimes;
    }

    @Override
    public void startElement(String url, String localName, String qName, Attributes attributes) {
        if (qName.equals("voter")) {
            try {
                String key = attributes.getValue("name") + "\t" + attributes.getValue("birthDay");
                voterCounts.put(key, voterCounts.getOrDefault(key, 0) + 1);

                if (voterCounts.size() >= 100_000) {
                    DBConnection.executeBatchInsert(voterCounts);
                    voterCounts.clear();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (qName.equals("visit")) {
            int currentStation = Integer.parseInt(attributes.getValue("station"));
            try {
                Date currentTime = visitDateFormat.parse(attributes.getValue("time"));
                WorkTime workTime = voteStationWorkTimes.computeIfAbsent(currentStation, k -> new WorkTime());
                workTime.addVisitTime(currentTime.getTime());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void endDocument() {
        try {
            if (!voterCounts.isEmpty()) {
                DBConnection.executeBatchInsert(voterCounts);
                System.out.println("Final batch insert executed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
