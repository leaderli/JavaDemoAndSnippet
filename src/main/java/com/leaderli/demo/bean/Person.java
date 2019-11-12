package com.leaderli.demo.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Person {
    @Autowired
    BeAutowired beAutowired;
    @PostConstruct
    public void begin(){
        System.out.println(" beAutowired= " + beAutowired);
    }
    
    public void end(){
        System.out.println("end");
    }
}
