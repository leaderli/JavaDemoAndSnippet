package io.leaderli.demo.thread;

import io.leaderli.litool.core.util.ThreadUtil;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Exchanger;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author leaderli
 * @since 2022/5/22
 */
public class ExchangeTest {


    @Test
    public void test() throws InterruptedException {

        Exchanger<String> exchanger = new Exchanger<>();

        new Thread(() -> {
            try {
                System.out.println("a change");
                System.out.println("a:"+exchanger.exchange("a"));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        new Thread(() -> {
            try {
                System.out.println("b sleep");
                Thread.sleep(1000);
                System.out.println("b change");
                System.out.println("b:"+exchanger.exchange("b"));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        Thread.sleep(2000);

    }
}
