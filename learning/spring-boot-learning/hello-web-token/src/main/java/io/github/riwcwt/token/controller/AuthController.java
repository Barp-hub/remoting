package io.github.riwcwt.token.controller;

import io.github.riwcwt.token.util.SecurityUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by michael on 2017-06-08.
 */
@Controller
public class AuthController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public String validate(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password, HttpServletResponse response) throws UnsupportedEncodingException {
        if ("michael".equalsIgnoreCase(username) && "michael".equalsIgnoreCase(password)) {
            Cookie token = new Cookie("token", SecurityUtil.encode(username, Date.from(Instant.now().plusSeconds(1800))));
            token.setMaxAge(1800);
            response.addCookie(token);
            return "redirect:/";
        }
        return "redirect:/login";
    }

}
