package com.leaderli.demo.reactor;

public interface Subscriber<T> {
    
    void onSubscribe(Subscription subscription);
    
    void next(T t);
    
    void onError(Throwable t);
    
    void onComplete();
}
