package io.leaderli.demo.util;

import org.apache.commons.lang3.RandomUtils;

import java.util.Date;
import java.util.Random;

public class SomeUtils {

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static Random random = new Random();

    public static int random(int boundary) {
        return random.nextInt(boundary);
    }

    public static void logWithThread(Object msg) {
        System.out.println("[" + Thread.currentThread().getName() + " " + new Date().getSeconds() + "]" + " " + msg);
    }

    public static void join(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
