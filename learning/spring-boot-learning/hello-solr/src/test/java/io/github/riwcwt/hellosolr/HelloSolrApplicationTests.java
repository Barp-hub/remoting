package io.github.riwcwt.hellosolr;

import com.alibaba.fastjson.JSON;
import io.github.riwcwt.hellosolr.document.Events;
import io.github.riwcwt.hellosolr.repository.EventRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloSolrApplicationTests {

    @Autowired
    private EventRepository repository = null;

    @Test
    public void add() {
        Events event = new Events();
        event.setId(UUID.randomUUID().toString());
        event.setCountry("中国");
        event.setCity("杭州");
        this.repository.save(event);

    }

    @Test
    public void delete() {
        this.repository.deleteAll();
    }

    @Test
    public void search() {
        Pageable page = new PageRequest(1, 10);
        System.out.println(JSON.toJSONString(this.repository.search("中国", page)));
    }
}
