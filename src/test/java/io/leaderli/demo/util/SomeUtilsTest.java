package io.leaderli.demo.util;


import org.junit.jupiter.api.Test;
class SomeUtilsTest {
    @Test
    public void test() {
        for (int i = 0; i < 100; i++) {
            int random = SomeUtils.random(4);
            System.out.println("random = " + random);
        }
    }

}
