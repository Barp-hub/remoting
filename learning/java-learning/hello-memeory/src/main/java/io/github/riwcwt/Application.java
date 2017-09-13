package io.github.riwcwt;

import io.github.riwcwt.model.Entity;

import java.io.IOException;

/**
 * Hello world!
 */
public class Application {

    private static Entity[] entities = new Entity[1024 * 128];

    public static void main(String[] args) throws IOException {

        for (int i = 0; i < entities.length; i++) {
            entities[i] = new Entity("hello");
        }

        System.in.read();
    }
}
