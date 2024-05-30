import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.Connection.Response;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.RecursiveAction;

public class CrawlTask extends RecursiveAction {
    private final String url;
    private final int depth;
    private final Set<String> visitedUrls;

    public CrawlTask(String url, int depth, Set<String> visitedUrls) {
        this.url = url;
        this.depth = depth;
        this.visitedUrls = visitedUrls;
    }

    @Override
    protected void compute() {
        if (depth <= SiteMapCrawler.MAX_DEPTH && visitedUrls.add(url)) {
            try {
                Response response = Jsoup.connect(url).execute();
                String contentType = response.contentType();

                if (contentType != null && (contentType.startsWith("text/") || contentType.contains("xml") || contentType.contains("json"))) {
                    Document doc = response.parse();
                    Thread.sleep(SiteMapCrawler.PAUSE_TIME_MS);

                    if (depth < SiteMapCrawler.MAX_DEPTH) {
                        Elements links = doc.select("a[href]");
                        List<CrawlTask> tasks = new ArrayList<>();
                        for (Element link : links) {
                            String childUrl = link.absUrl("href");
                            if (isValidUrl(childUrl) && !visitedUrls.contains(childUrl)) {
                                CrawlTask task = new CrawlTask(childUrl, depth + 1, visitedUrls);
                                task.fork();
                                tasks.add(task);
                            }
                        }
                        for(CrawlTask task : tasks) {
                            task.join();
                        }
                    }
                    saveUrlToFile(url, depth);
                }
            } catch (IOException | InterruptedException e) {
                System.err.println("Error processing URL: " + url + " - " + e.getMessage());
            }
        }
    }

    private boolean isValidUrl(String url) {
        return url.startsWith(SiteMapCrawler.BASE_URL) &&
                !url.contains("#") &&
                !url.endsWith(".pdf") &&
                !url.endsWith(".jpg") &&
                !url.endsWith(".png") &&
                !url.endsWith(".zip");
    }

    private void saveUrlToFile(String url, int depth) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter("E:\\Skillbox\\java_basics\\Multithreading\\SiteMap\\src\\main\\java\\sitemap.txt", true))) {
            String indentedLink = "\t".repeat(Math.max(0, depth)) + url;
            writer.write(indentedLink + "\n");
            System.out.println("Saved URL: " + indentedLink);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

