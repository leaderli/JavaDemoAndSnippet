package com.leaderli.demo;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junitpioneer.jupiter.cartesian.CartesianTest;


/**
 * @author leaderli
 * @since 2022/2/20
 */
public class TestJunit {

    @ParameterizedTest
    @ValueSource(strings = { "racecar", "radar", "able was I ere I saw elba" })
    void parameters(String candidate) {
        System.out.println(candidate);
    }

    @CartesianTest
    void myCartesianTestMethod(
            @CartesianTest.Values(ints = { 1, 2 }) int x,
            @CartesianTest.Values(ints = { 3, 4 }) int y) {

        System.out.println(x+" "+ y);
    }

}
