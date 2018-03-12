package io.github.riwcwt;

import org.junit.Test;
import reactor.core.publisher.Flux;

public class ApplicationTest {

    @Test
    public void flux() {
        Flux.just("hello", "world").subscribe(System.out::print);

        Flux.generate(sink -> {
            sink.next("Hello");
            sink.complete();
        }).subscribe(System.out::print);

        Flux.create(sink -> {
            sink.next(2);
            sink.complete();
        }).subscribe(System.out::println);
    }

}
