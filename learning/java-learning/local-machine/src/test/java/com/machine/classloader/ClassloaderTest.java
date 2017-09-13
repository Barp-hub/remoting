package com.machine.classloader;

import org.junit.Test;
import sun.misc.Launcher;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.function.Consumer;
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

    @Test
    public void classPath() {
        ClassLoader cl = ClassLoader.getSystemClassLoader();

        URL[] urls = ((URLClassLoader) cl).getURLs();

        for (URL url : urls) {
            System.out.println(url.getFile());
        }
    }

    @Test
    public void level() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        while (true) {
            if (loader == null) {
                break;
            }
            if (loader instanceof URLClassLoader) {
                System.out.println(loader.toString());
                Arrays.stream(URLClassLoader.class.cast(loader).getURLs()).forEach(url -> System.out.println(url.getFile()));
            }
            loader = loader.getParent();
        }
    }
}
