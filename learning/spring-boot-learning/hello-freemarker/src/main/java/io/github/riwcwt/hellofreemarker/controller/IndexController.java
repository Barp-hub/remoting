package io.github.riwcwt.hellofreemarker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Controller
public class IndexController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/sse", method = RequestMethod.GET)
    public String sse() {
        return "sse";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(Model model) {
        model.addAttribute("header", "测试Header");
        return "home";
    }

    @ResponseBody
    @RequestMapping(value = "/emitter", method = RequestMethod.GET)
    public SseEmitter emitter() {
        SseEmitter emitter = new SseEmitter();

        new Thread(() -> {
            try {
                for (int i = 0; i < 100; i++) {
                    emitter.send(String.valueOf("当前时间：" + System.currentTimeMillis()));
                    Thread.sleep(1000);
                }
                emitter.complete();
            } catch (IOException e) {
                emitter.completeWithError(e);
            } catch (InterruptedException e) {
                emitter.completeWithError(e);
            }
        }).start();

        return emitter;
    }
}
