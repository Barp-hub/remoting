package io.github.riwcwt.token.controller;

import io.github.riwcwt.token.util.MemcacheCloner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class MemcacheController {

    private static final Logger logger = LoggerFactory.getLogger(MemcacheController.class);

    @ResponseBody
    @RequestMapping(value = "/memcache", method = RequestMethod.GET)
    public Map<String, String> memcache(@RequestParam(value = "n") int n) {
        Map<String, String> map = new HashMap<>();
        long start = System.currentTimeMillis();

        for (int i = 0; i < n; i++) {
            MemcacheCloner.set("key" + i, 600, "这是一段测试数据!" + Math.random());

            logger.info("获得值：" + MemcacheCloner.get("key" + i));

            MemcacheCloner.delete("key" + i);
        }

        map.put("耗时", String.valueOf(System.currentTimeMillis() - start));
        return map;
    }

}
