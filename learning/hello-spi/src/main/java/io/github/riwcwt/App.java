package io.github.riwcwt;

import java.util.ServiceLoader;

import io.github.riwcwt.spi.Car;

public class App {
	public static void main(String[] args) {
		ServiceLoader<Car> loaders = ServiceLoader.load(Car.class);
		for (Car car : loaders) {
			car.run();
		}
	}
}
