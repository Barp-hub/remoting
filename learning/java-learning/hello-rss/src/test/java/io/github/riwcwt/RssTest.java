package io.github.riwcwt;

import com.alibaba.fastjson.JSON;
import com.rometools.rome.feed.WireFeed;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.WireFeedInput;
import com.rometools.rome.io.XmlReader;
import io.github.riwcwt.entity.Feed;
import io.github.riwcwt.service.FeedService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RssTest {

    private static final Logger logger = LoggerFactory.getLogger(RssTest.class);

    @Autowired
    private FeedService feedService = null;

    @Test
    public void opml() throws IOException, FeedException {
        WireFeedInput input = new WireFeedInput();
        WireFeed feed = input.build(new File("src/main/resources/rss/feeds.xml"));
        logger.info(JSON.toJSONString(feed, true));
    }

    @Test
    public void feed() throws IOException, FeedException {

        Stream.of("http://justjavac.com/atom.xml", "http://tylermuth.wordpress.com/feed/", "http://www.cnblogs.com/cate/oracle/rss").forEach(line -> {
            try {
                URL feedUrl = new URL(line);
                SyndFeedInput input = new SyndFeedInput();
                SyndFeed feed = input.build(new XmlReader(feedUrl));

                //                logger.info(JSON.toJSONString(feed, true));
                logger.info("发布时间：" + feed.getPublishedDate());
                feed.getEntries().forEach(entry -> entry.getContents().forEach(content -> {
                    logger.info(entry.getPublishedDate() + " - " + content.getValue());
                }));
            } catch (FeedException | IOException e) {
                logger.error("获取feed流错误 : " + line, e);
            }

        });
    }

    @Test
    public void update() {

        AtomicInteger from = new AtomicInteger(Integer.MIN_VALUE);
        Integer count = 10;
        while (true) {
            List<Feed> list = this.feedService.listFeed(from.get(), count);
            if (list == null || list.isEmpty()) {
                break;
            }
            list.forEach(feed -> {
                if (feed.getId() > from.get()) {
                    from.set(feed.getId());
                }

                try {
                    URL feedUrl = new URL(feed.getUrl());
                    SyndFeedInput input = new SyndFeedInput();
                    SyndFeed syndFeed = input.build(new XmlReader(feedUrl));
                    if (syndFeed.getPublishedDate() != null) {
                        if (feed.getLastUpdateDate() == null) {
                            this.feedService.updateFeedLastUpdateDate(feed.getId(), syndFeed.getPublishedDate().getTime());
                        } else {
                            if (syndFeed.getPublishedDate().getTime() > feed.getLastUpdateDate()) {
                                this.feedService.updateFeedLastUpdateDate(feed.getId(), syndFeed.getPublishedDate().getTime());
                            }
                        }
                    }
                } catch (FeedException | IOException e) {
                    logger.error("获取feed流错误 : " + feed.getUrl(), e);
                }
            });
        }

    }

}
