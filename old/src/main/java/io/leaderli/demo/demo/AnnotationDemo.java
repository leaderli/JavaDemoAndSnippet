package io.leaderli.demo.demo;


@MyAnnotation
public class AnnotationDemo {


    public static void main(String[] args) {
        MyAnnotation myAnnotation = AnnotationDemo.class.getAnnotation(MyAnnotation.class);
        myAnnotation.value();
    }

}
