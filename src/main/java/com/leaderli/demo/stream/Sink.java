package com.leaderli.demo.stream;

import java.util.function.Consumer;

public abstract class Sink<T> implements Consumer<T> {

    public Sink next;

    public final Sink prev;


    public Sink(Sink<T> prev) {
        this.prev = prev;
        if (prev != null) {
            prev.next = this;
        }

    }

}
