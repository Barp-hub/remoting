package io.github.riwcwt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by michael on 2016-12-29.
 */
@Controller
public class IndexController {

    @ResponseBody
    @RequestMapping(value = "/performance", method = RequestMethod.GET)
    public Map<String, Object> performance() throws InterruptedException {
        Map<String, Object> result = new HashMap<>();
        int time = (int) (Math.random() * 15);
        Thread.sleep(time);
        result.put("time", time);
        return result;
    }
}
