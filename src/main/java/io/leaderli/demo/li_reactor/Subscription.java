package io.leaderli.demo.li_reactor;

public interface Subscription {

    void request(long n);

    void cancel();
}
