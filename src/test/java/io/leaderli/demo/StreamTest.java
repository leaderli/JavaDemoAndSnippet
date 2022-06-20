package io.leaderli.demo;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamTest {
    public static void main(String[] args) {

        Stream<Integer> begin = Stream.of(1, 2, 3, 4);
        begin.filter(item -> true).sorted().forEach(System.out::println);
    }

    public static <T> List<T> getDuplicateElement(List<T> list) {

        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }

        List<T> duplicate = new ArrayList<>();

        Set<T> uniq = new HashSet<>();

        for (T t : list) {
            if (!uniq.add(t)) {
                duplicate.add(t);
            }
        }

        return duplicate;

    }

    @Test
    public void test() {

        List<String> list = new ArrayList();

        for (int i = 0; i < 100000000; i++) {

            list.add(RandomStringUtils.random(3));
        }

        System.out.println(list.size());
        StopWatch stopWatch = new StopWatch();

        stopWatch.start();
        System.out.println(getDuplicateElement(list).size());

        stopWatch.stop();
        System.out.println(stopWatch.getTime(TimeUnit.MILLISECONDS));
        stopWatch.reset();
        stopWatch.start();

        Map<String, Long> result =


                list.stream().collect(
                        Collectors.groupingBy(Function.identity()
                                , Collectors.counting()
                        )
                );

        stopWatch.stop();
        System.out.println(stopWatch.getTime(TimeUnit.MILLISECONDS));


    }
}
