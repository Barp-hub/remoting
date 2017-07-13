package io.github.riwcwt.token.controller;

import io.github.riwcwt.token.annotation.Function;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by michael on 2017-06-08.
 */
@Controller
public class IndexController {

    @Function(value = "home")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

}
