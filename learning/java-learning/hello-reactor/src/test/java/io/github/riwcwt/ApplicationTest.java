package io.github.riwcwt;

import org.junit.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.IntStream;

public class ApplicationTest {

    @Test
    public void flux() {
        Flux.just("hello", "world").subscribe(System.out::println);

        Flux.generate(sink -> {
            sink.next("Hello");
            sink.complete();
        }).subscribe(System.out::println);

        Flux.create(sink -> {
            IntStream.range(1, 100).forEach(sink::next);
            sink.complete();
        }).subscribe(System.out::println);
    }

    @Test
    public void buffer() {
        Flux.range(1, 100).buffer(20).subscribe(System.out::println);
        Flux.interval(Duration.ofMillis(100)).buffer(Duration.ofMillis(1001)).take(2).toStream().forEach(System.out::println);
        Flux.range(1, 10).bufferUntil(i -> i % 2 == 0).subscribe(System.out::println);
        Flux.range(1, 10).bufferWhile(i -> i % 2 == 0).subscribe(System.out::println);
    }
}
