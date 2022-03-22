package com.leaderli.demo;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class BigDecimalTest {
    @Test
    public void test(){

        String maxBal = "10000.1";
        BigDecimal bigDecimal = new BigDecimal(maxBal);
        System.out.println("bigDecimal = " + bigDecimal);
        BigDecimal multiply = bigDecimal.multiply(new BigDecimal(0.01));

        System.out.println("multiply = " + multiply);
        System.out.println("multiply = " + multiply.doubleValue());

        DecimalFormat decimalFormat = new DecimalFormat("0.0000");
        String out = decimalFormat.format(multiply);
        System.out.println("out = " + out);
        System.out.println("out = " + Double.valueOf(out));

    }
}
