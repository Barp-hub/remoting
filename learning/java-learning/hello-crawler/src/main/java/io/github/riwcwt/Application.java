package io.github.riwcwt;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import io.github.riwcwt.crawler.GoogleCrawler;

/**
 * Hello world!
 */
public class Application {
    public static void main(String[] args) throws Exception {

        String crawlStorageFolder = "./data";
        int numberOfCrawlers = 1;

        CrawlConfig config = new CrawlConfig();
        config.setSocketTimeout(200000);
        config.setConnectionTimeout(300000);
        config.setCrawlStorageFolder(crawlStorageFolder);

        config.setIncludeHttpsPages(true);

        /*
         * Instantiate the controller for this crawl.
         */
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        robotstxtConfig.setEnabled(false);
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        /*
         * For each crawl, you need to add some seed urls. These are the first
         * URLs that are fetched and then the crawler starts following links
         * which are found in these pages
         */
        controller.addSeed("https://www.cnblogs.com/");

        /*
         * Start the crawl. This is a blocking operation, meaning that your code
         * will reach the line after this only when crawling is finished.
         */
        controller.start(GoogleCrawler.class, numberOfCrawlers);

    }
}
