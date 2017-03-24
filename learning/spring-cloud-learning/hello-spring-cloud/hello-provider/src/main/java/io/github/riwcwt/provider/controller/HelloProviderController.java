package io.github.riwcwt.provider.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by michael on 2017-03-24.
 */
@RestController
public class HelloProviderController {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public Map<String, Object> hello(@RequestParam(value = "username") String username) {
        Map<String, Object> map = new HashMap<>();
        map.put("username", username);
        map.put("server", 2);
        return map;
    }

}
