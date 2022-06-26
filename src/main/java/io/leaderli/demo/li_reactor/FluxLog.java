package io.leaderli.demo.li_reactor;

class FluxLog<T> implements Flux<T> {


    private Publisher<? extends T> prevPublisher;

    public FluxLog(Publisher<? extends T> prevPublisher) {
        this.prevPublisher = prevPublisher;
    }

    @Override
    public void subscribe(Subscriber<? super T> actualSubscriber) {
        prevPublisher.subscribe(new LogSubscriber<T>(actualSubscriber));

    }

}
