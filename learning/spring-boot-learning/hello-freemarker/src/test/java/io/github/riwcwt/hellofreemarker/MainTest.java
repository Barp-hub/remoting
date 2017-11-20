package io.github.riwcwt.hellofreemarker;

import org.junit.Test;

import java.util.stream.IntStream;

public class MainTest {

    @Test
    public void stream() {
        IntStream.range(0, 10).parallel().forEach(value -> System.out.println(value));
    }

}
