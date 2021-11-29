package com.leaderli.demo.reactor;

class FluxArray<T> extends Flux<T> {
    private T[] array;
    
    public FluxArray(T[] array) {
        this.array = array;
    }
    
    @Override
    public void suscribe(Subscriber<? super T> actual) {
        actual.onSubscribe(new ArraySubscrition<>(actual, array));
    }
    
    static class ArraySubscrition<T> implements Subscription {
        private T[] arr;
        private Subscriber<? super T> actual;
        
        private int index = 0;
        
        private boolean canceled;
        
        public ArraySubscrition(Subscriber<? super T> actual, T[] array) {
            this.actual = actual;
            this.arr = array;
        }
        
        @Override
        public void request(long n) {
            if (canceled) {
                return;
            }
            if (n < 0) {
                n = arr.length;
            }
            for (int i = 0; i < n && index < arr.length; i++) {
                
                try {
                    
                    actual.next(arr[index++]);
                    if (canceled) {
                        return;
                    }
                } catch (Throwable throwable) {
                    actual.onError(throwable);
                    return;
                }
            }
            
            if (index == arr.length) {
                actual.onComplete();
            }
            
        }
        
        @Override
        public void cancel() {
            canceled = true;
        }
    }
}
