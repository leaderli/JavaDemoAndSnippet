package com.leaderli.demo.reactor;


import org.apache.log4j.Logger;
import org.junit.Test;


class FluxTest {
    private static final Logger log = Logger.getLogger(FluxTest.class);
    
    
    @Test
    public void testArray() {
        FluxArray<Integer> flux = Flux.range(10);
        flux.map(i -> i * i).suscribe(new Subscriber<Integer>() {
            
            @Override
            public void onSubscribe(Subscription subscription) {
                subscription.request(-1);
            }
            
            @Override
            public void next(Integer integer) {
                
                log.debug("next[" + integer + "]");
                int i = integer / (integer - 3);
                
            }
            
            @Override
            public void onError(Throwable t) {
                
                t.printStackTrace();
            }
            
            @Override
            public void onComplete() {
                log.debug("onComplete");
                
            }
        });
        
    }
    
    
}