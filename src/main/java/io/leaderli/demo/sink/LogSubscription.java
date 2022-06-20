package io.leaderli.demo.sink;

/**
 * @author leaderli
 * @since 2022/6/21
 */
class LogSubscription extends LiPublisher123 {
    private final LiPublisher123 actual;

    LogSubscription(LiPublisher123 actual) {
        this.actual = actual;
    }

    public void whenRegister(LiSubscription subscription) {
        System.out.println("onSubscribe " + subscription);
        subscription.request();
    }

    public void next(String next) {

        System.out.println(Integer.toHexString(actual.hashCode()) + " " + next);


        actual.next("->" + next);


    }

}
