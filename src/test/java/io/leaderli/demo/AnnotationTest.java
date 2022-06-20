package io.leaderli.demo;


import java.lang.annotation.Annotation;
import java.util.function.Function;

public class AnnotationTest  {
    public static void main(String[] args) {


        FunctionalInterface annotation = Function.class.getAnnotation(FunctionalInterface.class);
        Class<? extends Annotation> annotationType = annotation.annotationType();

    }
}

