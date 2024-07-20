package io.leaderli.demo.stream;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class Pipeline<IN, OUT> implements Stream<OUT> {

    final Pipeline prev;
    List<IN> source;
    Consumer<OUT> next;


    Pipeline(List<IN> source) {
        this.prev = null;
        this.source = source;
    }

    Pipeline(Pipeline<?, IN> prev) {
        this.prev = prev;
    }

    private static class Head<T> extends Pipeline<T, T> {

        Head(List<T> source) {
            super(source);

        }

        @Override
        public Consumer<T> sink() {
            for (T t : source) {
                next.accept(t);
            }
            return null;
        }

        public static <T> Head<T> staticOfList(T[] arr) {
            return new Head<>(Arrays.asList(arr));

        }
    }

    @Override
    public Stream<OUT> ofList(OUT[] arr) {
        return new Head<OUT>(Arrays.asList(arr)) {

        };

    }

    @Override
    public Stream<OUT> filter(Predicate<OUT> predicate) {
        return new Pipeline<OUT, OUT>(this) {
            @Override
            public Consumer<OUT> sink() {
                return consumer -> {
                    if (predicate.test(consumer)) {
                        next.accept(consumer);
                    }
                };
            }
        };
    }

    @Override
    public <R> Stream<R> mapper(Function<? super  OUT, R> mapper) {
        return new Pipeline<OUT, R>(this) {
            @Override
            public Consumer<OUT> sink() {
                return consumer -> next.accept(mapper.apply(consumer));
            }
        };
    }

    @Override
    @SuppressWarnings("unchecked")
    public void forEach(Consumer<OUT> consumer) {
        Pipeline<OUT, OUT> pipeline = new Pipeline<OUT, OUT>(this) {
            @Override
            public Consumer<OUT> sink() {
                return consumer;
            }
        };

        while (pipeline.prev != null) {
            Pipeline prev = pipeline.prev;
            prev.next = pipeline.sink();
            pipeline = prev;
        }
        Consumer<OUT> next = pipeline.next;
        System.out.println(next);
        pipeline.sink();
    }

    public static void main(String[] args) {
        Head.staticOfList(new Integer[]{1, 2, 3}).filter(i -> i > 1).mapper(i -> i + "--").forEach(System.out::println);
    }

}
