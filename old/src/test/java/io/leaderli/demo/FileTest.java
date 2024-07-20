package io.leaderli.demo;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @author leaderli
 * @since 2022/4/24
 */
public class FileTest {


    public void name(String file) {

        final String regex = "/(?=[^/]+$)";

        System.out.println(Arrays.toString(file.split(regex)));
    }

    @Test
    public void test() {

        name("/abc/1");
        name("1");
        name("1.log");


    }
}
