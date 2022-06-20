package io.leaderli.demo.li_reactor;

class FluxArray<T> implements Flux<T> {
    private T[] array;

    public FluxArray(T[] array) {
        this.array = array;
    }

    @Override
    public void subscribe(Subscriber<? super T> actual) {
        actual.onSubscribe(new ArraySubscription<>(actual, array));
    }

}
