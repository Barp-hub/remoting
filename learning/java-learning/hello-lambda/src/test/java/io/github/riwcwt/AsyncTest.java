package io.github.riwcwt;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Created by michael on 2017-03-31.
 */
public class AsyncTest {

    @Test
    public void async() {
        List<Integer> list = Arrays.asList(10, 20, 30, 40);
        list.stream().map(data -> CompletableFuture.supplyAsync(() -> getNumber(data))).
                map(compFuture -> compFuture.thenApply(n -> n * n)).map(t -> t.join())
                .forEach(System.out::println);
    }

    private static int getNumber(int a) {
        return a * a;
    }

    @Test
    public void completable() {
        CompletableFuture.supplyAsync(() -> 2)
                .thenApplyAsync(AsyncTest::getNumber)
                .thenAcceptAsync(i -> System.out.println(i))
                .whenCompleteAsync((result, error) -> System.out.println(result + " Error:" + error));
    }

}
