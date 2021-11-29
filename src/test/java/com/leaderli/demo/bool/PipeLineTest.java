package com.leaderli.demo.bool;

import org.junit.Assert;
import org.junit.Test;
class PipeLineTest {
    @Test
    public void test() {
        PipeLine<Integer> pipeLine = new PipeLine<>();
        pipeLine.test(p -> p > 10).and().test(p -> p < 15).end();
        Assert.assertTrue(pipeLine.accept(11));
        Assert.assertFalse(pipeLine.accept(15));
        Assert.assertFalse(pipeLine.accept(10));
        
        pipeLine = new PipeLine<>();
        pipeLine.test(p -> p > 10).or().test(p -> p == 10).end();
        Assert.assertTrue(pipeLine.accept(11));
        Assert.assertTrue(pipeLine.accept(10));
        Assert.assertFalse(pipeLine.accept(0));
        
        pipeLine = new PipeLine<>();
        pipeLine.not().test(p -> p <= 10).or().test(p -> p == 10).end();
        Assert.assertTrue(pipeLine.accept(11));
        Assert.assertTrue(pipeLine.accept(10));
        Assert.assertFalse(pipeLine.accept(0));
        
        pipeLine = new PipeLine<>();
        pipeLine.not().test(p -> p <= 10).or().not().test(p -> p != 10).end();
        Assert.assertTrue(pipeLine.accept(11));
        Assert.assertTrue(pipeLine.accept(10));
        Assert.assertFalse(pipeLine.accept(0));
        
    }
}