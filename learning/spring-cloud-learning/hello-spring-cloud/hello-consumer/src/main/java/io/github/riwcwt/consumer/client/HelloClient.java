package io.github.riwcwt.consumer.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * Created by michael on 2017-03-24.
 */
@FeignClient(value = "service-provider", fallback = HelloClientHystrix.class)
public interface HelloClient {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    Map<String, Object> hello(@RequestParam(value = "username") String username);

}
