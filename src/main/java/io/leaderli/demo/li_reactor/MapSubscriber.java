package io.leaderli.demo.li_reactor;

import java.util.function.Function;

/**
 * 作为中间层节点，其大部分方法都是通用的，唯一需要修改的就是 next 方法的实际逻辑
 */


final class MapSubscriber<T, R> extends MiddleSubscriber<T, R> {

    private final Function<? super T, ? extends R> mapper;

    private Subscription prevSubscription;

    public MapSubscriber(Function<? super T, ? extends R> mapper, Subscriber<R> actualSubscriber) {
        super(actualSubscriber);
        this.mapper = mapper;
    }

    @Override
    public void next(T t) {

        R apply = mapper.apply(t);
        this.actualSubscriber.next(apply);
    }


}
