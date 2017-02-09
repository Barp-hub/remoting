package io.github.riwcwt.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by michael on 2017-02-09.
 */
public class HelloService {
    private static final Logger logger = LoggerFactory.getLogger(HelloService.class);

    private String environment;

    public void setEvnironment(String environment) {
        this.environment = environment;
    }


    public void print() {
        logger.info("当前运行的环境为：" + this.environment);
    }
}
