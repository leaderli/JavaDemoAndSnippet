package com.leaderli.demo;

import org.junit.jupiter.api.Test;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;

/**
 * @author leaderli
 * @since 2022/5/17
 */
public class RedissionTest {


    public static void main(String[] args) {
        //1. 配置部分
        Config config = new Config();
        String address = "redis://192.168.142.128:6379";
        SingleServerConfig serverConfig = config.useSingleServer();
        serverConfig.setAddress(address);
        serverConfig.setDatabase(0);
        config.setLockWatchdogTimeout(5000);
        Redisson redisson = (Redisson) Redisson.create(config);


        RLock rLock = redisson.getLock("goods:1000:1");


        for (int i = 0; i < 4; i++) {

            new Thread(() -> {

                //2. 加锁
                rLock.lock();
                try {

                    System.out.println("todo 逻辑处理 1000000." + Thread.currentThread().getId());
                    Thread.sleep(300000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (rLock.isLocked() && rLock.isHeldByCurrentThread()) {
                        //3. 解锁
                        rLock.unlock();
                    }
                }
                System.out.println("release " + Thread.currentThread().getId());
            }).start();
        }

    }

    @Test
    public void test() {

        String lua = "if (redis.call('exists', KEYS[1]) == 0) then " +
                "redis.call('hincrby', KEYS[1], ARGV[2], 1); " +
                "redis.call('pexpire', KEYS[1], ARGV[1]); " +
                "return nil; " +
                "end; " +
                "if (redis.call('hexists', KEYS[1], ARGV[2]) == 1) then " +
                "redis.call('hincrby', KEYS[1], ARGV[2], 1); " +
                "redis.call('pexpire', KEYS[1], ARGV[1]); " +
                "return nil; " +
                "end; " +
                "return redis.call('pttl', KEYS[1]);";

    }
}
