package com.leaderli.demo.reactor;

public interface Processor<T, R> extends Subscriber<T>, Publisher<R> {
}
