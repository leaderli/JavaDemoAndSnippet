package io.leaderli.demo.util;


import java.util.Calendar;
import java.util.Random;

public class SomeUtils {

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        }
    }

    static Random random = new Random();

    public static int random(int boundary) {
        return random.nextInt(boundary);
    }

    public static void logWithThread(Object msg) {

        System.out.println("[" + Thread.currentThread().getName() + " " + Calendar.getInstance().get(Calendar.SECOND) + "]" + " " + msg);
    }

    public static void join(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException ignored) {
        }
    }

}
