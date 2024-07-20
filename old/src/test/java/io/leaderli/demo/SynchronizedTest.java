package io.leaderli.demo;

public class SynchronizedTest {

    public static void main(String[] args) {
        Object lock = new Object();
        new Thread(() -> {
            synchronized (lock) {
                //not run
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("1");
            }
        }).start();
        new Thread(() -> {
            synchronized (lock) {
                //not run
                System.out.println("2");
            }
        }).start();
    }
}
