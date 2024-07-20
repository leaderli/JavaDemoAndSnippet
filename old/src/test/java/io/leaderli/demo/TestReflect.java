package io.leaderli.demo;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Arrays;

public class TestReflect {

    public static class Hello {
        @Override
        public String toString() {
            return "hello 123";
        }
    }


    class Parent{
        public int size;
    }

    class Son extends Parent{
        public int age;
    }
    @Test
    public void test() throws Throwable {

        Hello hello = new Hello();

        System.out.println(hello);

        Method m1 = Hello.class.getMethod("toString");
        Method m2 = Object.class.getMethod("toString");
        System.out.println("invoke hello "+ m1.invoke(hello));
        System.out.println("invoke object "+ m2.invoke(hello));
        System.out.println(m1);
        System.out.println(m2);
        System.out.println(m1 == m2);

    }

    @Test
    public void test2() throws Throwable{

        System.out.println(Arrays.toString(Son.class.getFields()));

    }
}
