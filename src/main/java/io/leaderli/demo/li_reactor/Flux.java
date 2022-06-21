package io.leaderli.demo.li_reactor;

import io.vavr.collection.Array;

import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public interface Flux<T> extends Publisher<T> {

    @SafeVarargs
    static <T> Flux<T> just(T... array) {
        return new FluxArray<T>(array);
    }

    static FluxArray<Integer> range(int range) {
        Integer[] arr = new Integer[range];
        for (int i = 0; i < range; i++) {
            arr[i] = i;
        }
        return new FluxArray<>(arr);
    }


    default <R> Flux<R> map(Function<T, R> mapper) {

        return new FluxMap<>(this, mapper);
    }


    default void subscribe(BiConsumer<? super T,Subscription> consumer) {
        this.subscribe(new ConsumerSubscriber<>(consumer));
    }
}
