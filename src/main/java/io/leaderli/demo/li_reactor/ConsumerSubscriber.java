package io.leaderli.demo.li_reactor;

import java.util.function.Consumer;

/**
 * @author leaderli
 * @since 2022/6/21
 */
class ConsumerSubscriber<T> implements Subscriber<T> {

    private final Consumer<? super T> consumer;

    public ConsumerSubscriber(Consumer<? super T> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        subscription.request(-1);
    }

    @Override
    public void next(T t) {
        consumer.accept(t);
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
