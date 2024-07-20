package io.leaderli.demo.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author leaderli
 * @since 2022/5/22
 */
public class SemaphoreTest {


    @Test
    public void test() throws InterruptedException {

        Semaphore semaphore = new Semaphore(0);

        boolean b = semaphore.tryAcquire(5, TimeUnit.SECONDS);

        System.out.println(b);


        semaphore.release();
        b = semaphore.tryAcquire(5, TimeUnit.SECONDS);


        System.out.println(b);
    }
}
