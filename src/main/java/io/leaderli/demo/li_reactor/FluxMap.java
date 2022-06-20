package io.leaderli.demo.li_reactor;

import java.util.function.Function;

class FluxMap<T, R> implements Flux<R> {
    private Function<? super T, ? extends R> mapper;

    private Flux<? extends T> source;

    public FluxMap(Flux<? extends T> source, Function<T, R> mapper) {
        this.source = source;
        this.mapper = mapper;
    }

    @Override
    public void subscribe(Subscriber<? super R> actual) {
        source.subscribe(new MapSubscriber<T, R>(mapper, actual));

    }

}
