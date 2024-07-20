package io.leaderli.demo;

import org.junit.jupiter.api.Test;

/**
 * @author leaderli
 * @since 2022/3/17 4:30 PM
 */
public class MathTest {
    @Test
    public void test() throws Throwable {

        int x = 315;
        int x1 = x - Math.floorMod(x, 30);
        System.out.println(x1);
        System.out.println(Math.floorDiv(x, 30));


    }
}
