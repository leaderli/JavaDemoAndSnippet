package io.leaderli.demo;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SynchronousSink;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class TestReactor {
    private static List<String> words = Arrays.asList(
            "the",
            "quick",
            "brown",
            "fox",
            "jumped",
            "over",
            "the",
            "lazy",
            "dog"
    );

    @Test
    public void simpleCreation() {
        Flux<String> fewWords = Flux.just("Hello", "World");
        Flux<String> manyWords = Flux.fromIterable(words);

        fewWords.subscribe(System.out::println);
        System.out.println();
        manyWords.subscribe(System.out::println);
    }

    @Test
    public void test() throws Throwable {

        Flux.fromIterable(Arrays.asList("a", "b", "c"))
                .concatWith(Mono.just("d"))
                .distinct()
                .sort()
                .zipWith(Flux.range(1, Integer.MAX_VALUE), (string, count) -> String.format("%2d. %s", count, string))
                .subscribe(System.out::println);

//        System.out.println();
//        manyLetters.subscribe(System.out::println);
//        Thread.sleep(100000);
    }

    public static void main(String[] args) throws InterruptedException {


        Flux.generate(() -> 0, (Integer state, SynchronousSink<String> sink) -> {

                    if (state == 1) {
                        sink.next("true");
                        return 0;
                    }

                    sink.next("false");
                    return 1;
                })
                .take(5)
                .map(String::toUpperCase)
                .subscribe(System.out::println);

        Thread.sleep(100000);
    }

    @Test
    public void test3() throws Throwable {

        Flux.<String>create(sink -> sink.next("123"))
                .subscribe(System.out::println);

    }

    private static class SinkHolder<T> implements Consumer<FluxSink<T>> {
        private FluxSink<T> fluxSink;

        @Override
        public void accept(FluxSink<T> fluxSink) {
            this.fluxSink = fluxSink;
        }

        public void emit(T value) {
            this.fluxSink.next(value);
        }
    }

    @Test
    public void test4() throws Throwable {


        SinkHolder<String> holder = new SinkHolder<>();
        Flux.<String>create(holder);

        CountDownLatch countDownLatch = new CountDownLatch(1);

        new Thread(() -> {


            holder.emit("hello");
            holder.emit("reactor");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            holder.emit("end");

            countDownLatch.countDown();

        }).start();
        countDownLatch.await();

    }


    private static class SlowSubscriber<T> implements Subscriber<T> {

        private final int consumeMills;
        private Subscription subscription;

        private SlowSubscriber(int consumeMills) {
            this.consumeMills = consumeMills;
        }


        @Override
        public void onSubscribe(Subscription subscription) {

            this.subscription = subscription;
            this.subscription.request(1);
        }

        @Override
        public void onNext(T t) {

            try {
                Thread.sleep(consumeMills);
                System.out.println("SlowSubscriber:sleep " + consumeMills + " get " + t);
                this.subscription.request(1);

            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }

        @Override
        public void onError(Throwable throwable) {

            throwable.printStackTrace();
        }

        @Override
        public void onComplete() {

        }
    }

    @Test
    public void test5() throws Throwable {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        SinkHolder<Integer> holder = new SinkHolder<>();

        Flux.create(holder,
                        FluxSink.OverflowStrategy.BUFFER
//                        FluxSink.OverflowStrategy.DROP
//                        FluxSink.OverflowStrategy.ERROR
//                        FluxSink.OverflowStrategy.LATEST
                )
                .onBackpressureBuffer()
//                .onBackpressureDrop(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) {
//                        System.out.println("drop "+integer);
//                    }
//                })
//                .onBackpressureError()
//                .onBackpressureLatest()
//                .limitRate(4)
                .publishOn(Schedulers.newSingle("consumer"), 1)
                .subscribe(new SlowSubscriber<>(1000));


        AtomicInteger counter = new AtomicInteger();
        new Thread(() -> {

            while (true) {

                holder.emit(counter.getAndIncrement());
                System.out.println(counter.get());
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (counter.get() > 10) {
                    break;
                }
            }

        }).start();
        countDownLatch.await();

        Thread.sleep(10000);

    }

    @Test
    public void test6() throws Throwable {

        Flux.fromArray(new String[]{"1", "2"}).subscribe(System.out::println);
        Flux.fromIterable(Arrays.asList("1", "2")).subscribe(System.out::println);
        // stream转换为Flux只能被订阅一次
        Flux.fromStream(Stream.of("1", "2")).subscribe(System.out::println);

        Mono.fromCallable(() -> {
            Thread.sleep(1000);
            return "123";
        }).subscribe(System.out::println);
    }

    @Test
    public void test7() throws Throwable {
        Flux.fromArray(new String[]{"1", "2"}).doOnNext(sink -> {
            System.out.println("doOnNext:" + sink);
        }).subscribe(System.out::println);
    }

    @Test
    public void test8() throws Throwable {


        CountDownLatch latch = new CountDownLatch(1);
        Flux.just("1").delaySubscription(Duration.ofMillis(2000)).subscribe(next -> {
            System.out.println(next);
            latch.countDown();
        });

        latch.await();

    }

    @Test
    public void test9() throws Throwable {

        Flux.range(0, 10).skip(5).subscribe(System.out::println);

    }

    @Test
    public void test10() throws Throwable {

        Flux<String> empty = Flux.just("1");

        for (int i = 0; i < 10000; i++) {

            int finalI = i;
            empty = empty.map(str -> str);
        }
        System.out.println("-------->" + empty.subscribe(System.out::println));

    }
}
