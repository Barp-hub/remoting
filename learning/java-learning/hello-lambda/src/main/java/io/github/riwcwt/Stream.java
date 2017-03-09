package io.github.riwcwt;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by michael on 2017-03-09.
 */
public class Stream {
    public static void main(String[] args) {
        Arrays.asList(1, 2, 3, 2, 1, 65, 21).stream().filter(i -> i % 2 == 1).map(i -> i * i).collect(Collectors.toSet()).forEach(System.out::println);
    }
}
