package io.leaderli.demo.li_reactor;

import org.junit.jupiter.api.Test;

/**
 * @author leaderli
 * @since 2022/6/20
 */
public class FluxTest2 {

    @Test
    public void test() {


//        reactor.core.publisher.Flux.empty().error(new RuntimeException()).subscribe(System.out::println);
        reactor.core.publisher.Flux.empty() .subscribe(System.out::println);

    }
}
