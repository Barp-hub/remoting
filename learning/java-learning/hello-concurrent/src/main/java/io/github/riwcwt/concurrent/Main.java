package io.github.riwcwt.concurrent;

import java.util.Scanner;

/**
 * Created by michael on 2017-03-13.
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String command = scanner.nextLine();
            if ("exit".equalsIgnoreCase(command)) {
                break;
            }
            System.out.println(command);
        }
    }
}
