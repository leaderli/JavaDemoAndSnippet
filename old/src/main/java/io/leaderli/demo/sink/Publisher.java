package io.leaderli.demo.sink;

/**
 * @author leaderli
 * @since 2022/6/21
 */
public class Publisher {

    protected final String value;

    public Publisher(String value) {
        this.value = value;
    }

    public Publisher log() {

        return new LogPublisher(this);

    }

    public void register(LiPublisher123 publisher) {
        System.out.println("1------------"+publisher);

        publisher.whenRegister(new LiSubscription(publisher, value));
    }

}

