package io.leaderli.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
public class TestEnum {

    @Test
    public void test() throws Throwable {
        for (Color enumConstant : Color.class.getEnumConstants()) {
            System.out.println(enumConstant);
        }

    }

    @Test
    public void test2() {


        TextFragment<Color> text = new TextFragment<>(Color.GREEN);
        Assertions.assertSame(Color.GREEN, text.mode);
    }

    enum Color implements Mode<Color> {
        RED, GREEN;

        @Override
        public Color combine() {
            return this;
        }
    }

    public enum B implements A<String> {
        A1(), A2;

        @Override
        public String getValue() {
            return null;
        }
    }

    interface Mode<E extends Enum<E>> {
        public E combine();
    }

    interface A<T> {
        T getValue();
    }

    class TextFragment<E extends Enum<E> & Mode<E>> {

        E mode;
        Mode<E> mode2;

        public TextFragment(E mode) {
            this.mode = mode;
            this.mode2 = mode;
        }
    }
}
