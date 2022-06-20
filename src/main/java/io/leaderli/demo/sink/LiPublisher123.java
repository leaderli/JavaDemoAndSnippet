package io.leaderli.demo.sink;

/**
 * @author leaderli
 * @since 2022/6/21
 */
public class LiPublisher123 {

    public void whenRegister(LiSubscription subscription) {
        System.out.println("onSubscribe1 "+ subscription);
        subscription.request();
    }

    public void next(String next) {

        System.out.println("--->" + next);
    }

}
