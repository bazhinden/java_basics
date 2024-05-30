import java.util.Set;
import java.util.Collections;
import java.util.TreeSet;
import java.util.concurrent.ForkJoinPool;

public class SiteMapCrawler {
    public static final String BASE_URL = "https://sendel.ru/";
    public static final int PAUSE_TIME_MS = 200;
    public static final int MAX_DEPTH = 3;

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        Set<String> visitedUrls = Collections.synchronizedSet(new TreeSet<>());
        pool.invoke(new CrawlTask(BASE_URL, 0, visitedUrls));
    }
}