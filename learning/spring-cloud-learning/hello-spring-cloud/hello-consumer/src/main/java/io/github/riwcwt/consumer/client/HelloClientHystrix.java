package io.github.riwcwt.consumer.client;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by michael on 2017-03-24.
 */
public class HelloClientHystrix implements HelloClient {

    @Override
    public Map<String, Object> hello(String username) {
        Map<String, Object> map = new HashMap<>();
        map.put("error", "调用错误啦！");
        return map;
    }

    @Override
    public Map<String, Object> index() {
        Map<String, Object> map = new HashMap<>();
        map.put("error", "调用服务错误啦！");
        return map;
    }

}
