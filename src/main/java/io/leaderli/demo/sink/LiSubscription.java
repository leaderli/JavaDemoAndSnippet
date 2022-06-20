package io.leaderli.demo.sink;

/**
 * @author leaderli
 * @since 2022/6/21
 */
public class LiSubscription {

    private LiPublisher123 subscriber;
    protected final String value;

    public LiSubscription(LiPublisher123 subscriber, String value) {
        this.subscriber = subscriber;
        this.value = value;
    }

    public void request() {
        System.out.println(" request " + subscriber);

        subscriber.next(value);
    }

}
