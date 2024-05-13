package io.leaderli.demo.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceTest {

    @Test
    void test() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        executor.submit(()-> System.out.println("123"));
        executor.submit(()-> System.out.println("456"));

        executor.awaitTermination(1, TimeUnit.SECONDS);


    }
}
