package io.leaderli.demo.li_reactor;

/**
 * @author leaderli
 * @since 2022/6/22
 */
public abstract class MiddleSubscriber<T, R> implements Subscriber<T>, Subscription {
    protected final Subscriber<? super R> actualSubscriber;
    private Subscription prevSubscription;

    public MiddleSubscriber(Subscriber<? super R> actualSubscriber) {
        this.actualSubscriber = actualSubscriber;
    }

    @Override
    public void onSubscribe(Subscription prevSubscription) {
        this.prevSubscription = prevSubscription;
        actualSubscriber.onSubscribe(this);
    }

    @Override
    public void onError(Throwable t) {

        this.actualSubscriber.onError(t);
    }

    @Override
    public void onComplete() {

        this.actualSubscriber.onComplete();

    }

    @Override
    public void request() {
        this.prevSubscription.request();

    }

    @Override
    public void cancel() {
        this.prevSubscription.cancel();
    }
}
