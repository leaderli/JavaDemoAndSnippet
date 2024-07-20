package io.leaderli.demo.demo;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Demo2 {


    public static void main(String[] args) {
        List<Integer> list = new LinkedList<>();

        List<Integer> sync = Collections.synchronizedList(list);

    }
}


