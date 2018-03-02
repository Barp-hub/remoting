package io.github.riwcwt.hellospringboottwo.controller;

import io.github.riwcwt.hellospringboottwo.model.Customer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class IndexController implements InitializingBean {

    private Map<Long, Customer> map = new HashMap<>();

    @GetMapping(value = "/")
    public Flux<Customer> all() {
        return Flux.fromIterable(map.entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toList()));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        map.put(Long.valueOf(1), new Customer(1, "Jack", "Smith", 20));
        map.put(Long.valueOf(2), new Customer(2, "Peter", "Johnson", 25));
    }
}
