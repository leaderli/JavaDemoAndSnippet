package io.leaderli.demo.li_reactor;

import java.util.function.Function;

/**
 * @author leaderli
 * @since 2022/6/21
 */
final class MapSubscriber<T, R> implements Subscriber<T>, Subscription {
    private boolean done;
    private Function<? super T, ? extends R> mapper;
    private Subscriber<? super R> actual;

    private Subscription array;

    public MapSubscriber(Function<? super T, ? extends R> mapper, Subscriber<? super R> actual) {
        this.mapper = mapper;
        this.actual = actual;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        this.array = subscription;
        actual.onSubscribe(this);
    }

    @Override
    public void next(T t) {

        if (done) {
            return;
        }
        R apply = mapper.apply(t);
        this.actual.next(apply);
    }

    @Override
    public void onError(Throwable t) {

        if (done) {
            return;
        }
        done = true;
        this.actual.onError(t);
    }

    @Override
    public void onComplete() {
        if (done) {
            return;
        }
        done = true;
        this.actual.onComplete();

    }

    @Override
    public void request(long n) {
        this.array.request(n);

    }

    @Override
    public void cancel() {
        this.array.cancel();
    }
}
