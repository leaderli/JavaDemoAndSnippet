package io.leaderli.demo.sink;

/**
 * @author leaderli
 * @since 2022/6/21
 */
class LogPublisher extends Publisher {

    private final Publisher origin;

    public LogPublisher(Publisher origin) {
        super(origin.value);
        this.origin = origin;
    }

    public void register(LiPublisher123 publisher) {

        System.out.println(origin + " subscribe " + publisher);
        origin.register(new LogSubscription(publisher));
    }
}
