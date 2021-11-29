package com.leaderli.demo.bean;

public interface BooleanSink {
    
    boolean cancel();
    
    void accept(boolean result);
}
