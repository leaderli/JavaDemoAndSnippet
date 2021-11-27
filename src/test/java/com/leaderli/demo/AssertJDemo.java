package com.leaderli.demo;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class AssertJDemo {

    public static void main(String[] args) {

    }

    @Test
    public void test() {
        Assertions.assertThat(testMethod()).hasSize(4)
                .contains("some result", "some other result")
                .doesNotContain("shouldn't be here");


    }

    private List<String> testMethod() {
        return new ArrayList<>();
    }
}
