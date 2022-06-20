package io.leaderli.demo.sink;

import org.junit.jupiter.api.Test;

/**
 * @author leaderli
 * @since 2022/6/21
 */
class LiRegisterTest {

    @Test
    public void test() {
        new Publisher("123").log().log().log().log().log().log().register(new LiPublisher123() {
            @Override
            public void next(String next) {
                System.out.println("******" + next);
            }

            @Override
            public String toString() {
                return "custom li publisher ";
            }
        });

    }

}
