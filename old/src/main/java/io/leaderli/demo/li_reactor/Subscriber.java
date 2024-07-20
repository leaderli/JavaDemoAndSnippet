package io.leaderli.demo.li_reactor;

/**
 * 订阅者
 */
public interface Subscriber<T> {

    /**
     * 当订阅动作发起时调用，通常情况下 onSubscribe 是通知链上的下一个节点 onSubscribe ，最终到链的尾
     * 部，链式调用最尾端的订阅者订阅时，将向前一个节点发送一个{@link Subscription#request()}请
     * 求，一直向前传递，直到数据来源的发布者接收到来自链上的数据请求，然后调用 {@link #next(Object)}，
     * 链上的每个节点一般依次调用 {@link #next(Object)} 最终会调用订阅者的 {@link #next(Object)}
     */
    void onSubscribe(Subscription subscription);

    /**
     * 发布者推送了一个消息
     */
    void next(T t);

    /**
     * 发布者提交了一个错误事件
     */
    void onError(Throwable t);

    /**
     * 发布者提交一个完成事件
     */
    void onComplete();
}
