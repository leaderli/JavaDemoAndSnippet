package io.leaderli.demo.li_reactor;

/**
 * @author leaderli
 * @since 2022/6/21
 */
class ArraySubscription<T> implements Subscription {

    private final T[] arr;
    private final Subscriber<? super T> actualSubscriber;


    private boolean canceled;

    public ArraySubscription(Subscriber<? super T> actualSubscriber, T[] arr) {
        this.actualSubscriber = actualSubscriber;
        this.arr = arr;
    }

    @Override
    public void request() {
        if (canceled) {
            return;
        }

        for (T t : arr) {

            try {

                actualSubscriber.next(t);
                // 通过 onSubscribe 将 Subscription 传递给订阅者，由订阅者来调用 cancel方法从而实现提前结束循环
                if (canceled) {
                    return;
                }
            } catch (Throwable throwable) {
                actualSubscriber.onError(throwable);
                return;
            }
        }

        actualSubscriber.onComplete();

    }

    @Override
    public void cancel() {
        canceled = true;
    }
}
