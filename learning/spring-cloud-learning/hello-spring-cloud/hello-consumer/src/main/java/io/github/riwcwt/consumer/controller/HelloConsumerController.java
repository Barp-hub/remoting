package io.github.riwcwt.consumer.controller;

import io.github.riwcwt.consumer.client.HelloClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by michael on 2017-03-24.
 */
@RestController
public class HelloConsumerController {

    @Autowired
    private HelloClient helloClient = null;

    @Value(value = "${name}")
    private String name = null;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public Map<String, Object> hello(@RequestParam(value = "username") String username) {
        return this.helloClient.hello(username);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Map<String, Object> index() {
        Map<String, Object> data = this.helloClient.index();
        data.put("name", name);
        return data;
    }

}
