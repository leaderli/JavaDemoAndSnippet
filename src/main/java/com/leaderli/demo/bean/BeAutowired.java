package com.leaderli.demo.bean;

import org.springframework.stereotype.Component;

@Component
public class BeAutowired {
    static {
        System.out.println(BeAutowired.class+"--------static-------");
    }
}
