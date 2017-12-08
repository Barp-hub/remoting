package io.github.riwcwt.reflect;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;
import java.util.stream.Stream;

public class ReflectionTest {

    @Test
    public void construct() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Stream.of(String.class.getConstructors()).map(constructor -> constructor.getParameterCount()).forEach(System.out::println);

        Constructor<String> constructor = String.class.getConstructor(byte[].class);

        String content = constructor.newInstance(new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15});
        System.out.println(content);
    }

    @Test
    public void field() {
        Stream.of(String.class.getDeclaredFields()).map(field -> field.toGenericString()).forEach(System.out::println);
    }

}
