package io.leaderli.demo.bean;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author leaderli
 * @since 2022/5/22
 */

class BeAutowiredTest {

    @Test
    public void test() {


        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.scan(BeAutowired.class.getPackage().getName());
        applicationContext.refresh();
        System.out.println(applicationContext.getBean(BeAutowired.class));

    }
}
