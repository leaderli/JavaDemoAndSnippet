package io.leaderli.demo.sink;

import java.util.function.Predicate;

public interface LiOperationSink<T> {
    LiPredicateSink<T> test(Predicate<T> predicate);
}
