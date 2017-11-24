package com.machine;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

public class StringTest {

    @Test
    public void test() throws IOException {
        System.out.println(JSON.toJSONString(FileUtils.readLines(new java.io.File("C:\\Users\\michael\\Desktop\\告警uuid.告警uuid")).stream().filter(org.apache.commons.lang3.StringUtils::isNotBlank).toArray(), true));
    }

    @Test
    public void stream() {
        IntStream.range(23, 49).parallel().forEach(System.out::println);
    }

}
