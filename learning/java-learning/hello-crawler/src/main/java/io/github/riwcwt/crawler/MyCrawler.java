package io.github.riwcwt.crawler;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

public class MyCrawler extends WebCrawler {

    private static final Logger logger = LoggerFactory.getLogger(MyCrawler.class);

    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|ico|gif|jpg|png|mp3|mp4|zip|gz))$");

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        if (url.getDomain().equalsIgnoreCase("riwcwt.github.io")) {
            if (!FILTERS.matcher(url.getURL().toLowerCase()).matches()) {
                logger.info(url.getURL());
                return true;
            }
        }
        return false;
    }

    @Override
    public void visit(Page page) {

        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();

            String html = htmlParseData.getHtml();

            Document document = Jsoup.parse(html);
            Elements elements = document.select(".content");
            elements.stream().forEach(element -> {
                logger.info(element.html());
            });
        }

    }
}
