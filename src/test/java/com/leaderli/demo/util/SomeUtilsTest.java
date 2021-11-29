package com.leaderli.demo.util;


import org.junit.Test;

class SomeUtilsTest {
    @Test
    public void test() {
        for (int i = 0; i < 100; i++) {
            int random = SomeUtils.random(4);
            System.out.println("random = " + random);
        }
    }
    
}