package io.github.riwcwt.service;

import io.github.riwcwt.entity.Feed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

}
