package io.leaderli.demo;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author leaderli
 * @since 2022/1/27 1:39 PM
 */
public class CommonTest {


    private void lazy(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void test() throws Throwable{

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        lazy();
        stopWatch.stop();
        stopWatch.reset();
        stopWatch.start();
        lazy();
        stopWatch.stop();

        System.out.println(stopWatch.getTime(TimeUnit.MILLISECONDS));
    }
}
