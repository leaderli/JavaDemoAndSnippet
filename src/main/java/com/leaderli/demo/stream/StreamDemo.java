package com.leaderli.demo.stream;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class StreamDemo {


    private abstract static class Sink<T> implements Consumer<T> {
        public Sink next;
        public Sink prev;


        Sink(Sink<T> prev) {
            if (prev != null) {
                this.prev = prev;
                prev.next = this;
            }

        }


    }

    private static class Head<T> extends Sink<T> {

        Head(Sink<T> prev) {
            super(prev);
        }


        @Override
        public void accept(T t) {
            next.accept(t);
        }
    }

    private static class Filter<T> extends Sink<T> {

        private Predicate<T> predicate;

        Filter(Sink<T> prev, Predicate<T> predicate) {
            super(prev);
            this.predicate = predicate;
        }


        @Override
        public void accept(T t) {
            if (predicate.test(t)) {
                next.accept(t);
            }
        }
    }

    private static class Map<T, R> extends Sink<T> {

        private Function<T, R> mapper;


        Map(Sink<T> prev, Function<T, R> mapper) {
            super(prev);

            this.mapper = mapper;
        }


        @Override
        public void accept(T t) {
            R apply = mapper.apply(t);
            next.accept(apply);
        }
    }

    private static class End<T> extends Sink<T> {

        End(Sink<T> prev) {
            super(prev);
        }


        @Override
        public void accept(T t) {
            System.out.println(t);


        }
    }

    public static void main(String[] args) {

        Head<Integer> head = new Head<>(null);
        Filter<Integer> filter = new Filter<>(head, i -> i > 2);
        Map<Integer, String> mapper = new Map<>(filter, i -> i + "--");
        Filter<String> filter2 = new Filter(mapper, i -> ((String)i).startsWith("3"));
        End<Integer> end = new End(filter2);

        Sink<Integer> prev = end;
        while (prev.prev != null) {
            prev = prev.prev;
        }
        for (int t : Arrays.asList(1, 2, 3, 4)) {
            prev.accept(t);
        }

    }
}
