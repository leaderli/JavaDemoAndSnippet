package com.leaderli.demo;

import org.junit.Test;

public class ClassTest {

    @Test
    public void test() {

        Integer[] arr =new Integer[0];

        System.out.println(arr.getClass().getComponentType());
        System.out.println(this.getClass().getComponentType());

    }
}
