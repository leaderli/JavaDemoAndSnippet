package io.leaderli.demo.li_reactor;

class FluxArray<T> implements Flux<T> {
    private final T[] array;

    public FluxArray(T[] array) {
        this.array = array;
    }

    @Override
    public void subscribe(Subscriber<T> actualSubscriber) {
        // 让实际的订阅者去相应订阅时的动作，对于 ConsumerSubscriber 来说 就是请求数据源，对于 MapSubscriber来说，就是去找他的订阅者
        actualSubscriber.onSubscribe(new ArraySubscription<>(actualSubscriber, array));
    }


}
