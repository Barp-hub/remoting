package io.github.riwcwt;


import io.github.riwcwt.config.ApplicationConfig;
import io.github.riwcwt.service.HelloService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//import org.springframework.core.env.AbstractEnvironment;

public class Application {
    public static void main(String[] args) {

        //System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "product");

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        //context.getEnvironment().setActiveProfiles("develop");
        context.register(ApplicationConfig.class);
        context.refresh();

        HelloService helloService = context.getBean(HelloService.class);
        helloService.print();

        context.close();
    }
}
