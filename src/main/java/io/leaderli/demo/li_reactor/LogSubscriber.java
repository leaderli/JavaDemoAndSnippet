package io.leaderli.demo.li_reactor;


final class LogSubscriber<T> extends MiddleSubscriber<T, T> {


    public LogSubscriber(Subscriber<? super T> actualSubscriber) {
        super(actualSubscriber);
    }

    @Override
    public void next(T t) {

        System.out.println("li:" + t);
        this.actualSubscriber.next(t);
    }
}
