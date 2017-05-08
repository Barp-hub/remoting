package io.github.riwcwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by michael on 2016-12-29.
 */
@Controller
public class IndexController {

    @Autowired
    private RestTemplate restTemplate;

    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Map<String, Object> index() throws InterruptedException {
        Map<String, Object> result = new HashMap<>();
        int time = (int) (Math.random() * 15);
        Thread.sleep(time);
        result.put("now", System.currentTimeMillis());
        result.put("user", "michael");
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/performance", method = RequestMethod.GET)
    public Map<String, Object> performance() throws InterruptedException {
        Map<String, Object> result = new HashMap<>();
        int time = (int) (Math.random() * 15);
        Thread.sleep(time);
        result.put("time", time);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/network", method = RequestMethod.GET)
    public Map<String, Object> network() throws InterruptedException, UnknownHostException {
        Map<String, Object> result = new HashMap<>();
        result.put("ip", InetAddress.getLocalHost().getHostAddress());
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/web", method = RequestMethod.GET)
    public String web(@RequestParam(value = "url", required = false) String url) {
        if (url == null) {
            url = "https://www.baidu.com/";
        }
        return this.restTemplate.getForEntity(url, String.class).getBody();
    }
}
