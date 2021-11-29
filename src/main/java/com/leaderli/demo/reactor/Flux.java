package com.leaderli.demo.reactor;

import java.util.function.Function;

public abstract class Flux<T> implements Publisher<T> {
    
    @SafeVarargs
    public static <T> Flux<T> just(T... array) {
        return new FluxArray<T>(array);
    }
    
    public static FluxArray<Integer> range(int range) {
        Integer[] arr = new Integer[range];
        for (int i = 0; i < range; i++) {
            arr[i] = i;
        }
        return new FluxArray<>(arr);
    }
    
    
    public <R> Flux<R> map(Function<T, R> mapper) {
        
        return new FluxMap<>(this, mapper);
    }
    
    
}
