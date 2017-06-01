package io.github.riwcwt.consumer.service;

import io.github.riwcwt.hello.model.HelloDTO;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by michael on 2017-05-31.
 */
@Component
public class HelloService implements InitializingBean {

    @Resource(name = "hello-service")
    private io.github.riwcwt.hello.api.HelloService helloService;

    @Override
    public void afterPropertiesSet() throws Exception {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                HelloDTO hello = new HelloDTO();
                hello.setName("michael");
                String content = helloService.say(hello);
                System.out.println(content);
            }
        }, 0, 2000);
    }

}
