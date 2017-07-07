package io.github.riwcwt.terminal.controller;

import com.jcabi.ssh.SSH;
import com.jcabi.ssh.Shell;
import io.github.riwcwt.terminal.model.Command;
import io.github.riwcwt.terminal.model.Message;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;

/**
 * Created by michael on 2017-07-06.
 */
@Controller
public class TerminalController {

    private static final Logger logger = LoggerFactory.getLogger(TerminalController.class);

    @RequestMapping(value = "/ws", method = RequestMethod.GET)
    public String index() {
        return "ws";
    }

    @ResponseBody
    @RequestMapping(value = "/command", method = RequestMethod.GET)
    public String command() throws IOException {
        Shell shell = new SSH("192.168.1.15", 22, "root", FileUtils.readFileToString(new File("/root/.ssh/id_rsa")));
        String hello = new Shell.Plain(shell).exec("echo 'Hello, world!'");
        return hello;
    }

    @MessageMapping("/shell")
    @SendTo("/topic/command")
    public Message execute(Command command) {
        logger.info("命令：" + command.toString());
        Message message = new Message();
        message.setContent(command.toString() + "执行完毕！");
        return message;
    }
}
