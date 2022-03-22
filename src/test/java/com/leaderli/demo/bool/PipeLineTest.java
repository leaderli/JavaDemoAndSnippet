package com.leaderli.demo.bool;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;class PipeLineTest {
    @Test
    public void test() {
        PipeLine<Integer> pipeLine = new PipeLine<>();
        pipeLine.test(p -> p > 10).and().test(p -> p < 15).end();
        Assertions.assertTrue(pipeLine.accept(11));
        Assertions.assertFalse(pipeLine.accept(15));
        Assertions.assertFalse(pipeLine.accept(10));

        pipeLine = new PipeLine<>();
        pipeLine.test(p -> p > 10).or().test(p -> p == 10).end();
        Assertions.assertTrue(pipeLine.accept(11));
        Assertions.assertTrue(pipeLine.accept(10));
        Assertions.assertFalse(pipeLine.accept(0));

        pipeLine = new PipeLine<>();
        pipeLine.not().test(p -> p <= 10).or().test(p -> p == 10).end();
        Assertions.assertTrue(pipeLine.accept(11));
        Assertions.assertTrue(pipeLine.accept(10));
        Assertions.assertFalse(pipeLine.accept(0));

        pipeLine = new PipeLine<>();
        pipeLine.not().test(p -> p <= 10).or().not().test(p -> p != 10).end();
        Assertions.assertTrue(pipeLine.accept(11));
        Assertions.assertTrue(pipeLine.accept(10));
        Assertions.assertFalse(pipeLine.accept(0));

    }
}
