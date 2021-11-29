package com.leaderli.demo.stream;

import java.util.function.Consumer;

public abstract class Sink<T> implements Consumer<T> {

    public Sink next;
    public Sink prev;


    Sink(Sink<T> prev) {
        if (prev != null) {
            this.prev = prev;
            prev.next = this;
        }

    }

}
