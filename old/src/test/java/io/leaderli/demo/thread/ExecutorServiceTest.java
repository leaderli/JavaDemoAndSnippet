package io.leaderli.demo.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceTest {

    @Test
    void test() throws InterruptedException {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        executor.scheduleAtFixedRate(() -> System.out.println(1), 1,1, TimeUnit.SECONDS);

        executor.awaitTermination(5,TimeUnit.SECONDS);
    }
}
