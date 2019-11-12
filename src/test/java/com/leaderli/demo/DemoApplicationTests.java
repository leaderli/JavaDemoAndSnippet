package com.leaderli.demo;

import com.leaderli.demo.bean.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootTest
class DemoApplicationTests {
    
    @Autowired
    Person person;
	@Test
	void contextLoads() {
        System.out.println(person);
	}

}
