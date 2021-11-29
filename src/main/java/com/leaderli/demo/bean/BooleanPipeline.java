package com.leaderli.demo.bean;

import java.util.function.Predicate;

public class BooleanPipeline {
    
    public BooleanPipeline previousStage;
    
    public BooleanPipeline nextState;
    
    public BooleanSink sink;
    
    public BooleanPipeline() {
        this.previousStage = null;
        this.nextState = this;
    }
    
    public static BooleanPipeline begin() {
        
        return new BooleanPipeline();
    }
    
    public BooleanPipeline begin(Predicate predicate) {
        this.sink = new BooleanSink() {
            @Override
            public boolean cancel() {
                return false;
            }
            
            @Override
            public void accept(boolean result) {
            
            }
        };
        return this;
    }
}
