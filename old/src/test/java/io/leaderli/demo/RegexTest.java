package io.leaderli.demo;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @author leaderli
 * @since 2022/4/22
 */
public class RegexTest {


    @Test
    public void test() {


        String a = "123456 aa bb cc";
        String b = "123-456 aa bb cc";


        System.out.println(Arrays.toString(a.split("\\s+", 2)));
        System.out.println(Arrays.toString(a.split("\\s+")));
    }
}
