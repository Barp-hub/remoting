package io.github.riwcwt.reflect;

import org.junit.Test;

public class InterfaceTest {

    @Test
    public void invoke() {
        Animal a = (Human) () -> System.out.println("human");
        a.eat();
        a.food();
    }

    public static interface Animal {

        void eat();

        default void food() {
            System.out.println("other animals");
        }

    }

    public interface Human extends Animal {
        default void food() {
            System.out.println("everything");
        }
    }

    public interface Horse extends Animal {
        default void food() {
            System.out.println("grass");
        }
    }
}
