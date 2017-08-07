package io.github.riwcwt;

import io.github.riwcwt.config.AutoConfig;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Hello world!
 */
public class Application {
    public static void main(String[] args) throws IOException, InterruptedException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        context.start();

        AutoConfig config = context.getBean(AutoConfig.class);
        for (int i = 0; i < 10; i++) {
            System.out.println(config.getAuto());
            System.out.println(config.getName());
            Thread.sleep(10000);
        }

        context.stop();
        context.close();

    }
}
