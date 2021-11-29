package com.leaderli.demo.reactor;

public interface Publisher<T> {
    void suscribe(Subscriber<? super T> subscriber);
}
