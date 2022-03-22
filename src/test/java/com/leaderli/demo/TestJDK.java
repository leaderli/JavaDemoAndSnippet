package com.leaderli.demo;

import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;
import org.junit.jupiter.api.Test;
public class TestJDK {
    @Test
    public void test() throws Throwable{


        System.out.println("int[256]:\t\t"+ObjectSizeCalculator.getObjectSize(new int [256]));
        System.out.println("int[2][128]:\t"+ObjectSizeCalculator.getObjectSize(new int [2][128]));
        System.out.println("int[128][2]:\t"+ObjectSizeCalculator.getObjectSize(new int [128][2]));


    }
}
