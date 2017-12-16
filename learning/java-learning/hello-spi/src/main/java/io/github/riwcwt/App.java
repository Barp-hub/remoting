package io.github.riwcwt;

import io.github.riwcwt.spi.Car;

import java.util.ServiceLoader;

public class App {
    public static void main(String[] args) {
        ServiceLoader<Car> loaders = ServiceLoader.load(Car.class);
        for (Car car : loaders) {
            car.run();
        }
        loaders.forEach(Car::run);
    }
}
