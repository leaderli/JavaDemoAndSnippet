package com.leaderli.demo;

import java.util.ArrayList;
import java.util.List;

public class TheClassTest {


    public static void test(Object args){

        System.out.println(1);
    }
    public static void test(List<Object> args){
        System.out.println(2);

    }
    public static void main(String[] args) {
        test(new ArrayList<>());
        test(11);
    }
}



