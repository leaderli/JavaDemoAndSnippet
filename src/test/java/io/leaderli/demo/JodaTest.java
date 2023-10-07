package io.leaderli.demo;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;


public class JodaTest {

    @Test
    void test() {

        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyyMMdd");
        DateTime dateTime = formatter.parseDateTime("19900321");
        System.out.println(dateTime);

        System.out.println(dateTime.toString("hhMMss"));


    }
}
