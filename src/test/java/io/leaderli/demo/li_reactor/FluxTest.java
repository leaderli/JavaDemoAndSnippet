package io.leaderli.demo.li_reactor;


import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;

class FluxTest {
    private static final Logger log = Logger.getLogger(FluxTest.class);


    @Test
    public void testArray() {
        boolean a = true;
        FluxArray<Integer> flux = Flux.range(10);
        flux.map(i -> i * i).log().subscribe((num, s) -> {
            System.out.println(num);
            if (num > 5) {
                s.cancel();
            }
        });
    }


}
