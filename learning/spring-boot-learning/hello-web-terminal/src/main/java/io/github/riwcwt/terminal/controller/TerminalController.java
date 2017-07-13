package io.github.riwcwt.terminal.controller;

import io.github.riwcwt.terminal.socket.ShellSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.UUID;

/**
 * Created by michael on 2017-07-06.
 */
@Controller
public class TerminalController {

    private static final Logger logger = LoggerFactory.getLogger(TerminalController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        String token = UUID.randomUUID().toString();
        ShellSocket.addToken(token);
        model.addAttribute("token", token);
        model.addAttribute("ip", "192.168.1.4");
        return "index";
    }

}
