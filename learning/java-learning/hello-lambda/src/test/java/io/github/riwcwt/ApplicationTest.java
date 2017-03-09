package io.github.riwcwt;

import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Unit test for simple Application.
 */
public class ApplicationTest {
    @Test
    public void method() {
        final Car car = Car.create(Car::new);
        final List<Car> cars = Arrays.asList(car);

        cars.forEach(Car::collide);
        cars.forEach(Car::repair);
        final Car police = Car.create(Car::new);
        cars.forEach(police::follow);

    }

    @Test
    public void stream() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Stream<Integer> stream = numbers.stream();
        stream.map(i -> i * i).forEach(System.out::println);

        System.out.println(numbers.stream().map(i -> i * i).mapToInt(value -> value).sum());

        System.out.println(numbers.stream().filter(i -> i % 2 == 1).reduce(Integer.MIN_VALUE, Integer::max));


        List<Integer> nums = Arrays.asList(1, 1, null, 2, 3, 4, null, 5, 6, 7, 8, 9, 10);
        System.out.println("sum is:" + nums.stream().filter(num -> num != null).distinct().mapToInt(num -> num * 2).peek(System.out::println).skip(2).limit(4).sum());
        nums.stream().filter(num -> num != null).distinct().collect(Collectors.toList()).stream().forEach(System.out::println);
    }

    @Test
    public void sort() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 25, 6, 17, 8, 9, 10);
        list.sort(Comparator.naturalOrder());
        list.forEach(System.out::println);
    }


    @Test
    public void optional() {
        Optional.ofNullable("hello").ifPresent(System.out::println);

        Optional<String> firstName = Optional.of("Tom");
        System.out.println("First Name is set? " + firstName.isPresent());
        System.out.println("First Name: " + firstName.orElseGet(() -> "[none]"));
        System.out.println(firstName.map(s -> "Hey " + s + "!").orElse("Hey Stranger!"));
    }

    @Test
    public void parallel() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

        System.out.println(System.currentTimeMillis());
        numbers.parallelStream()
                .forEach(n -> {
                    try {
                        Thread.sleep(30000);
                        System.out.println(n);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
        System.out.println(System.currentTimeMillis());
    }

}

class Car {
    public static Car create(final Supplier<Car> supplier) {
        return supplier.get();
    }

    public static void collide(final Car car) {
        System.out.println("Collided " + car.toString());
    }

    public void follow(final Car another) {
        System.out.println("Following the " + another.toString());
    }

    public void repair() {
        System.out.println("Repaired " + this.toString());
    }
}
