package io.leaderli.demo.sink;

import java.util.function.Supplier;

public interface LiPredicateSink<T> extends Supplier<Boolean> {
    LiCombineOperationSink<T> and();

    LiCombineOperationSink<T> or();
}
