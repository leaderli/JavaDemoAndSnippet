package io.leaderli.demo;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;

public class ClassTest {

    @Test
    public void test() {

        Integer[] arr = new Integer[0];

        System.out.println(arr.getClass().getComponentType());
        System.out.println(this.getClass().getComponentType());

    }

    @Test
    public void test2() throws Throwable {


        CharSequence charSequence = "123";

        Class<? extends CharSequence> aClass = charSequence.getClass();

        done(charSequence);

    }

    private <T> void done(T t) {

        Class<T> aClass = (Class<T>) t.getClass();
        System.out.println(aClass);
    }

    @Test
    public void test1() {
        Object o = Array.newInstance(int.class, 1);
        System.out.println(o);
        Class<Integer> cls = int.class;
        System.out.println(cls.getTypeName());
        int [] ints = (int[]) Array.newInstance(cls, 1);
        Object obj= ints;


        Object [] arr = (Object[]) obj;
        System.out.println(arr);

    }
}
