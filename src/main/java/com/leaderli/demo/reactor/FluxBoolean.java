package com.leaderli.demo.reactor;

public abstract class FluxBoolean<T> implements Publisher<BoolBox<T>> {
    
    public BoolBox<T> box;
    
    public FluxBoolean<T> prev;
}
