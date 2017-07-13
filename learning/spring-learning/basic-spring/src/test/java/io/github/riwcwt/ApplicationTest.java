package io.github.riwcwt;

import io.github.riwcwt.beans.MyTestBean;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import io.github.riwcwt.config.ApplicationConfig;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

public class ApplicationTest {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationTest.class);

    @Test
    public void config() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        context.start();
        logger.info("context start...");
        context.stop();
        context.close();
    }

    @Test
    public void xml() {
        BeanFactory factory = new XmlBeanFactory(new ClassPathResource("application-context.xml"));

        MyTestBean bean = factory.getBean(MyTestBean.class);

        System.out.println(bean.getName());
    }

    @Test
    public void context() {
        ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");

        MyTestBean bean = context.getBean(MyTestBean.class);

        System.out.println(bean.getName());
    }
}
