package io.leaderli.demo.li_reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;

import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * @author leaderli
 * @since 2022/9/1 5:37 PM
 */
public class FluxTest3 {
    @Test
    void test() {

        Flux.generate(() -> 0, (state, sink) -> {
            sink.next(state);
            return state + 1;
        }).take(10).sort().subscribe(System.out::println);
//        Consumer<SynchronousSink<Integer>> consumer = synchronousSink -> {
//            synchronousSink.contextView().
//        };
//        Flux.<Integer>generate(consumer);

    }
}
