package com.leaderli.demo;

/**
 * @author leaderli
 * @since 2022/3/16
 */
public class ExtendsTest {


    static class A {
        public void start() {
            System.out.println("a");
        }
    }

    static class B extends A {

        @Override
        public void start() {
            System.out.println("b");
            super.start();
        }
    }

    static class C extends B {

        @Override
        public void start() {
            System.out.println("c");
            super.start();
        }
    }

    public static void main(String[] args) {
        new C().start();
    }
}
