package com.leaderli.demo.reactor;

import org.apache.log4j.Logger;

public class FluxBooleanEnd<T> extends FluxBoolean<T> {
    static final Logger LOGGER = Logger.getLogger(FluxBooleanEnd.class);
    
    public FluxBooleanEnd(FluxBoolean<T> prev, BoolBox<T> box) {
        this.prev = prev;
        this.box = box;
    }
    
    @Override
    public void suscribe(Subscriber<? super BoolBox<T>> subscriber) {
        this.prev.suscribe(new EndSubscriber<>(box));
        
    }
    
    private static class EndSubscriber<T> implements Subscriber<BoolBox<T>>, Subscription {
        
        public BoolBox<T> box;
        
        public EndSubscriber(BoolBox<T> box) {
            this.box = box;
        }
        
        @Override
        public void onSubscribe(Subscription subscription) {
            this.request(0);
        }
        
        @Override
        public void next(BoolBox<T> tBoolBox) {
            this.cancel();
        }
        
        
        @Override
        public void onError(Throwable t) {
            LOGGER.error(t);
            throw new RuntimeException(t);
        }
        
        @Override
        public void onComplete() {
        
        }
        
        @Override
        public void request(long n) {
        
        }
        
        @Override
        public void cancel() {
        
        }
    }
    
}