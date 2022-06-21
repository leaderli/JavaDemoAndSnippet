package io.leaderli.demo.li_reactor;

public interface Subscription {

    /**
     * 请求数据
     */
    void request();

    /**
     * 取消操作
     */
    void cancel();
}
