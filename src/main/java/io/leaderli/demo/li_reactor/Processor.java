package io.leaderli.demo.li_reactor;

public interface Processor<T, R> extends Subscriber<T>, Publisher<R> {
}
