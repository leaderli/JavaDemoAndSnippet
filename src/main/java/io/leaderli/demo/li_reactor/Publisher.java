package io.leaderli.demo.li_reactor;

/**
 * 发布者
 */
public interface Publisher<T> {
    /**
     * 订阅者在发布者上进行订阅操作, 发布者在得到订阅信息后，一般会依次将向前进行订阅，
     * 最终达到首个节点，则会去调用{@link Subscriber#onSubscribe(Subscription)}
     *
     * @see Subscriber#onSubscribe(Subscription)
     */
    void subscribe(Subscriber<? super T> subscriber);
}
