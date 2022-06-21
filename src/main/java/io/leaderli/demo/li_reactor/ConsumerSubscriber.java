package io.leaderli.demo.li_reactor;

import java.util.function.BiConsumer;

/**
 * @author leaderli
 * @since 2022/6/21
 */
class ConsumerSubscriber<T> implements Subscriber<T> {

    private final BiConsumer<? super T, Subscription> consumer;
    private Subscription subscription;

    public ConsumerSubscriber(BiConsumer<? super T, Subscription> consumer) {
        this.consumer = consumer;
    }

    @Override

    public void onSubscribe(Subscription subscription) {

        this.subscription = subscription;
        subscription.request();
    }

    @Override
    public void next(T t) {
        consumer.accept(t, this.subscription);
    }


    @Override
    public void onError(Throwable t) {

        t.printStackTrace();
    }

    @Override
    public void onComplete() {
        System.out.println("onComplete");

    }
}
