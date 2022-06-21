package io.leaderli.demo.li_reactor;

import java.util.function.Function;

class FluxMap<T, R> implements Flux<R> {

    private Function<? super T, ? extends R> mapper;

    private Publisher<T> prevPublisher;

    public FluxMap(Publisher<T> prevPublisher, Function<T, R> mapper) {
        this.prevPublisher = prevPublisher;
        this.mapper = mapper;
    }

    @Override
    public void subscribe(Subscriber<R> actualSubscriber) {
        // 作为一个中间节点，订阅者订阅到自己时，则需要将 mapper 函数 作为订阅者订阅到上一个 发布者
        Subscriber<T> subscriber = new MapSubscriber<>(mapper, actualSubscriber);
        prevPublisher.subscribe(subscriber);

    }

}
