package com.machine.classloader;

import org.junit.Test;
import sun.misc.Launcher;

import java.util.stream.Stream;

/**
 * Created by michael on 2017-07-03.
 */
public class ClassloaderTest {

    @Test
    public void bootstrapClassloader() {

        Stream.of(Launcher.getBootstrapClassPath().getURLs()).forEach(url -> System.out.println(url.toExternalForm()));
    }

    @Test
    public void extClassloader() {
        Stream.of(System.getProperty("java.ext.dirs").split(";")).forEach(System.out::println);
    }

    @Test
    public void appClassloader() {
        Stream.of(System.getProperty("java.class.path").split(";")).forEach(System.out::println);
    }
}
