package io.github.riwcwt.service;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import io.github.riwcwt.entity.Feed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class FeedService {
    private static final Logger logger = LoggerFactory.getLogger(FeedService.class);

    @Resource(name = "rss-jdbctemplate")
    private JdbcTemplate jdbcTemplate = null;

    public void addFeed(Feed feed) {
        this.jdbcTemplate.update("INSERT INTO feed(title, url, type, lastUpdateDate) VALUES (?, ?, ?, ?)", feed.getTitle(), feed.getUrl(), feed.getType(), feed.getLastUpdateDate());
    }

    public void updateFeedLastUpdateDate(Integer id, Long lastUpdateDate) {
        this.jdbcTemplate.update("UPDATE feed SET lastUpdateDate = ? WHERE id = ?", lastUpdateDate, id);
    }

    public List<Feed> listFeed(Integer id, Integer count) {
        return this.jdbcTemplate.query("select * from feed where id > ? order by id asc limit ?", new BeanPropertyRowMapper<>(Feed.class), id, count);
    }

    public Feed getFeed(Integer id) {
        return this.jdbcTemplate.queryForObject("select * from feed where id = ? ", Feed.class, id);
    }

    public void syncFeed(Integer id) {
        this.syncFeed(this.getFeed(id));
    }

    public void syncFeed(Feed feed) {
        try {
            URL feedUrl = new URL(feed.getUrl());
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed syndFeed = input.build(new XmlReader(feedUrl));
            if (syndFeed.getPublishedDate() != null) {
                if (feed.getLastUpdateDate() == null) {
                    this.updateFeedLastUpdateDate(feed.getId(), syndFeed.getPublishedDate().getTime());
                } else {
                    if (syndFeed.getPublishedDate().getTime() > feed.getLastUpdateDate()) {
                        this.updateFeedLastUpdateDate(feed.getId(), syndFeed.getPublishedDate().getTime());
                    }
                }
            }
        } catch (FeedException | IOException e) {
            logger.error("获取feed流错误 : " + feed.getUrl(), e);
        }
    }

    public void syncAllFeed() {
        AtomicInteger from = new AtomicInteger(Integer.MIN_VALUE);
        Integer count = 10;
        while (true) {
            List<Feed> list = this.listFeed(from.get(), count);
            if (list == null || list.isEmpty()) {
                break;
            }
            list.forEach(feed -> {
                if (feed.getId() > from.get()) {
                    from.set(feed.getId());
                }

                this.syncFeed(feed);
            });
        }
    }

}
