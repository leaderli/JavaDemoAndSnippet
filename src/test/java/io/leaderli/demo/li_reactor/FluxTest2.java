package io.leaderli.demo.li_reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.util.Iterator;

/**
 * @author leaderli
 * @since 2022/6/20
 */
public class FluxTest2 {

@Test
public void test() {

//        reactor.core.publisher.Flux.empty().error(new RuntimeException()).subscribe(System.out::println);
    reactor.core.publisher.Flux.empty().subscribe(System.out::println);
    Iterable<Integer> integers = new Iterable<Integer>() {

        @Override
        public Iterator<Integer> iterator() {
            return new Iterator<Integer>() {
                int i = 0;

                @Override
                public boolean hasNext() {
                    return true;
                }

                @Override
                public Integer next() {
                    return i++;
                }
            };
        }
    };
    Flux.range(1,10);
    for (Integer in : Flux.fromIterable(integers).toIterable()) {
        System.out.println(in);
    }


}
}
