package io.leaderli.demo.ast;

import io.leaderli.demo.bean.*;

import java.util.List;

public class PersonService {

    Person person = new Person();
    Person person3;

    void m1() {

        person.setFuck("123");
        Person person1 = new Person();
        person1.setFuck("123");


        io.leaderli.demo.bean.Person person2 = new Person();

        List<Integer> list;

        System.out.println("123");
    }
}
