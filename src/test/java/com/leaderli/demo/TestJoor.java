package com.leaderli.demo;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.joor.Reflect.onClass;

public class TestJoor {

    @Test
    public void test() throws Throwable {
        // All examples assume the following static import:

        String world = onClass(ArrayList.class) // Like Class.forName()
                .create()     // Call most specific matching constructor
                .call("add", 0, 6)      // Call most specific matching substring() method
                .call("clear")
                .call("toString")
                .get();                    // Get the wrapped object, in this case a String

        System.out.println(world);

    }

    public interface StringProxy {
        String substring(int beginIndex);
    }

    @Test
    public void test2() throws Throwable {


        String substring = onClass("java.lang.String")
                .create("Hello World")
                .as(StringProxy.class) // Create a proxy for the wrapped object
                .substring(6);         // Call a proxy method

        System.out.println(substring);

    }
}
