package io.leaderli.demo.bean;


import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BeAutowired {
    static {
        System.out.println("-------BeAutowired--------");
    }

    @Autowired
    public void set(BeanFactory beanFactory){
        System.out.println(beanFactory);
    }

}
