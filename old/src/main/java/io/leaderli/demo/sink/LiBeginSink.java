package io.leaderli.demo.sink;

public interface LiBeginSink<T> {

    LiCombineOperationSink<T> begin();
}
