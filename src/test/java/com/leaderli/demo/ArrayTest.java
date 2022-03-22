package com.leaderli.demo;


import org.junit.Test;

/**
 * @author leaderli
 * @since 2022/3/6
 */
public class ArrayTest {


    @Test
    public void test() {
        Object[] arr =new String[10];

        System.out.println(arr.getClass().getComponentType());

        System.out.println(String.class.getComponentType());
    }
}
