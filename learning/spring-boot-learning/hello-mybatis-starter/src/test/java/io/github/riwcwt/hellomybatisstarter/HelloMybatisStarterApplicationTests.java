package io.github.riwcwt.hellomybatisstarter;

import io.github.riwcwt.mapper.EventMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloMybatisStarterApplicationTests {

    @Autowired
    private EventMapper eventMapper = null;

    @Test
    public void contextLoads() {
        this.eventMapper.selectByPrimaryKey(5398L);
    }

}
