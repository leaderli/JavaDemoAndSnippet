package io.leaderli.demo;

public class Test1 {

    public void go() {
        int a = 567;

        Runnable runnable = ()->{
            int b = a;
            b=10086;
        };
    }

    public static void main(String[] args) {

    }
}
