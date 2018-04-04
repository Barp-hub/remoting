package io.github.riwcwt.crawler;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.url.WebURL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

public class GoogleCrawler extends WebCrawler {

    private static final Logger logger = LoggerFactory.getLogger(GoogleCrawler.class);

    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|ico|gif|jpg|png|mp3|mp4|zip|gz|svg|pdf))$");

    private final static Pattern PDF = Pattern.compile(".*(\\.(pdf))$");

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String link = url.getURL();
        String[] list = link.split("\\?");
        if (list.length > 0) {
            if (!FILTERS.matcher(list[0]).matches()) {
                return true;
            } else {
                if (PDF.matcher(list[0]).matches()) {
                    logger.info(url.getURL());
                }
            }
        }
        return false;
    }

    @Override
    public void visit(Page page) {
        super.visit(page);
    }
}
