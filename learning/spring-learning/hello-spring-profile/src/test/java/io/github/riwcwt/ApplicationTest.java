package io.github.riwcwt;

import io.github.riwcwt.config.ApplicationConfig;
import io.github.riwcwt.service.HelloService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ActiveProfiles("develop")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class})
public class ApplicationTest {

    @Autowired
    private HelloService helloService = null;

    @Test
    public void environment() {
        this.helloService.print();
    }
}
