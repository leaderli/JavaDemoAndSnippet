package com.leaderli.demo.reactor;

public interface Subscription {
    
    void request(long n);
    
    void cancel();
}
