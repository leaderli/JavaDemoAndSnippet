package io.leaderli.demo;

import org.junit.jupiter.api.Test;

import java.util.Date;

/**
 * @author leaderli
 * @since 2022/5/20
 */
public class DateTest {

    @Test
    public void test() {


        Date date = new Date();
        date.setTime(System.currentTimeMillis());
        System.out.println(date);


    }
}
