package io.github.riwcwt;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Scanner;

/**
 * Hello world!
 */
public class Application {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:application-context.xml");
        context.start();

        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        context.close();
    }
}
