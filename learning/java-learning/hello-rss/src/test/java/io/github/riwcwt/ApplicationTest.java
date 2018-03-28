package io.github.riwcwt;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationTest.class);

    @Resource(name = "rss-jdbctemplate")
    private JdbcTemplate jdbcTemplate = null;

    @Test
    public void select() {
        logger.info(JSON.toJSONString(this.jdbcTemplate.queryForMap("SELECT * FROM catalog")), true);
    }

    @Test
    public void insert() {
        this.jdbcTemplate.update("INSERT INTO CATALOG (name) VALUES (?)", "mysql");
    }
}
