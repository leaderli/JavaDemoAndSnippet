package io.leaderli.demo.li_reactor;

public interface Publisher<T> {
    void subscribe(Subscriber<? super T> subscriber);
}
