package com.leaderli.demo.reactor;

import java.util.function.Function;

class FluxMap<T, R> extends Flux<R> {
    private Function<? super T, ? extends R> mapper;
    
    private Flux<? extends T> source;
    
    public FluxMap(Flux<? extends T> source, Function<T, R> mapper) {
        this.source = source;
        this.mapper = mapper;
    }
    
    @Override
    public void suscribe(Subscriber<? super R> actual) {
        source.suscribe(new MapSubscriber<T, R>(mapper, actual));
        
    }
    
    static final class MapSubscriber<T, R> implements Subscriber<T>, Subscription {
        private boolean done;
        private Function<? super T, ? extends R> mapper;
        private Subscriber<? super R> actual;
        
        private Subscription subscriptionStream;
        
        public MapSubscriber(Function<? super T, ? extends R> mapper, Subscriber<? super R> actual) {
            this.mapper = mapper;
            this.actual = actual;
        }
        
        @Override
        public void onSubscribe(Subscription subscription) {
            this.subscriptionStream = subscription;
            actual.onSubscribe(this);
        }
        
        @Override
        public void next(T t) {
            
            if (done) {
                return;
            }
            R apply = mapper.apply(t);
            this.actual.next(apply);
        }
        
        @Override
        public void onError(Throwable t) {
            
            if (done) {
                return;
            }
            done = true;
            this.actual.onError(t);
        }
        
        @Override
        public void onComplete() {
            if (done) {
                return;
            }
            done = true;
            this.actual.onComplete();
            
        }
        
        @Override
        public void request(long n) {
            this.subscriptionStream.request(n);
            
        }
        
        @Override
        public void cancel() {
            this.subscriptionStream.cancel();
        }
    }
}
