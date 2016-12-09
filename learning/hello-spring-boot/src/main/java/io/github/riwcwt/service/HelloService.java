package io.github.riwcwt.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Created by michael on 2016-12-08.
 */
@Component
public class HelloService implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(HelloService.class);

    @Autowired
    private Environment environment = null;

    //@Value(value = "${programmer}")
    //private String programmer = null;

    @Override
    public void afterPropertiesSet() throws Exception {
        //logger.info("开发人员：" + programmer);

        logger.info("开发人员：" + environment.getProperty("programmer"));
    }

}
