package io.github.riwcwt.controller;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by michael on 2016-12-12.
 */
@Controller
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @ApiOperation(value = "索引页")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @ResponseBody
    @RequestMapping(value = "/sleep", method = RequestMethod.GET)
    public Map<String, String> sleep() {
        Map<String, String> data = new HashMap<>();
        data.put("username", "michael");
        try {
            Thread.sleep(120000);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
        logger.info("请求完成！");
        return data;
    }

}
