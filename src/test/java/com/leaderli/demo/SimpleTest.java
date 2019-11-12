package com.leaderli.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.InitDestroyAnnotationBeanPostProcessor;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;

public class SimpleTest {
    @Test
    public void test() {
        System.out.println(CommonAnnotationBeanPostProcessor.class.isAssignableFrom(InitDestroyAnnotationBeanPostProcessor.class
        ));
        System.out.println(InitDestroyAnnotationBeanPostProcessor.class.isAssignableFrom(CommonAnnotationBeanPostProcessor.class));
    }
}
