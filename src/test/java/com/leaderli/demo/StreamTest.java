package com.leaderli.demo;

import java.util.stream.Stream;

public class StreamTest {
    public static void main(String[] args) {
        
        Stream<Integer> begin = Stream.of(1, 2, 3, 4);
        begin.filter(item->true).sorted().forEach(System.out::println);
    }
}
