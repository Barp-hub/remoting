package io.github.riwcwt.actuator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class IndexController {

    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Map<String, Object> index() {
        Map<String, Object> result = new HashMap<>();
        result.put("name", "michael");
        return result;
    }
}
