package io.leaderli.demo;

import io.leaderli.litool.test.LiMock;
import io.leaderli.litool.test.LiTest;
import io.leaderli.litool.test.MockInit;

/**
 * @author leaderli
 * @since 2022/10/14 5:56 PM
 */
public class TestLiMock {

    public int m1() {

        return 1;
    }

    static void init() {

        LiMock.mock(TestLiMock.class);
        LiMock.when(() -> new TestLiMock().m1(), 1, 2);
    }

    @MockInit()
    @LiTest
    void test() {

        System.out.println("123");
        System.out.println(m1());
    }
}
