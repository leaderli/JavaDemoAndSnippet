package io.leaderli.demo;

import org.junit.jupiter.api.Test;

import java.text.MessageFormat;

/**
 * @author leaderli
 * @since 2022/1/30
 */
public class StringTest {

    @Test
    public void test() {


        System.out.println(String.format("flow[@name='%s']", "fuck"));
        System.out.println(MessageFormat.format("flow[@name='{0}']", "fuck"));
    }
}
