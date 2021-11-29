package com.leaderli.demo.reactor;

import java.util.function.Consumer;

public class FluxBooleanTest<T> extends FluxBoolean<T> {
    @Override
    public void suscribe(Subscriber<? super BoolBox<T>> subscriber) {
    
    }
    
    public void end(T checkValue, Consumer<Boolean> consumer) {
        this.suscribe(new Subscriber<BoolBox<T>>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                subscription.request(0);
            }
            
            @Override
            public void next(BoolBox<T> tBoolBox) {
            
            }
            
            @Override
            public void onError(Throwable t) {
                com.leaderli.demo.reactor.FluxBooleanEnd.LOGGER.error(t);
            }
            
            @Override
            public void onComplete() {
                consumer.accept(box.result);
            }
        });
    }
    
}
