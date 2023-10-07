package io.leaderli.demo.demo;

import java.util.List;

public class Demo1 {


    public <T extends Number> void m1(T t) {
        Number o = t;
    }

    public void m2(List<? super Integer> t) {
    }

    public void m3(List<? extends Integer> t) {
    }
}
