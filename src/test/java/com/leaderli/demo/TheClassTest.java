package com.leaderli.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TheClassTest {


    public static void test(Object args){

        System.out.println(1);
    }
    public static void test(List<Object> args){
        System.out.println(2);

    }

    @Test
    public void test() {

        Assertions.assertTrue(Object.class.isAssignableFrom(String.class));
        Assertions.assertTrue(String.class.isAssignableFrom(CharSequence.class));

    }

}



