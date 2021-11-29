package com.leaderli.demo.demo;

import com.leaderli.demo.util.SomeUtils;

public class ThreadTest {
    public static void main(String[] args) throws InterruptedException {
        Thread T1 = new Thread(() -> {
            System.out.println("T1");
            SomeUtils.sleep(3000);
        });
        Thread T2 = new Thread(() -> {
            System.out.println("T2");
            SomeUtils.sleep(2000);
        });
        Thread T3 = new Thread(() -> {
            System.out.println("T3");
            SomeUtils.sleep(1000);
        });
        
        T1.start();
        T1.join();
        T2.start();
        T2.join();
        T3.start();
        T3.join();
    }
}
