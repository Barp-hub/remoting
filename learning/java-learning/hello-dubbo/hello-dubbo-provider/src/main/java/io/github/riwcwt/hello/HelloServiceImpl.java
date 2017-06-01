package io.github.riwcwt.hello;

import io.github.riwcwt.hello.api.HelloService;
import io.github.riwcwt.hello.model.HelloDTO;
import org.springframework.stereotype.Service;

/**
 * Created by michael on 2017-05-27.
 */
@Service(value = "hello-service")
public class HelloServiceImpl implements HelloService {

    @Override
    public String say(HelloDTO hello) {
        return "hello, " + hello.getName() + "!";
    }

}
