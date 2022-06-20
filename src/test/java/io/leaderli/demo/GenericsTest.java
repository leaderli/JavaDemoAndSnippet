package io.leaderli.demo;

import org.junit.jupiter.api.Test;

import java.util.function.Function;
import java.util.function.Supplier;

public class GenericsTest<K, V> {
    public static void main(String[] args) {

        Bean<CharSequence, CharSequence> bean = new Bean<>();
        //mapper.apply请求参数为Object，返回参数为String
        //bean.apply请求参数CharSequence可以向上转型为Object，mapper返回参数String可以向上转型为CharSequenc
        bean.mapper = (Function<Object, String>) k -> "_" + k + "_";
        CharSequence apply = bean.apply("1");
    }

    enum NestEnum implements Supplier<NestEnum> {
        LIST;

        @Override
        public NestEnum get() {
            return this;
        }
    }

    static class Bean<K, V> {
        Function<? super K, ? extends V> mapper;

        //对于参数k，一定是实际mapper的实际泛型K1的子类
        //对于返回值v，一定是mapper的实际泛型V1的父类
        //因此我们可以将k向上转型为K1，mapper的返回值V1也可以向上转型为V
        V apply(K k) {
            return mapper.apply(k);
        }

    }

    private static class Nest<K> {

        public <T, P extends Pair<T, K>> void put(Class<T> type, P p) {

        }
    }

    private static class Pair<T, TW> {

        final Class<T> type;
        final TW tw;

        private Pair(Class<T> type, TW tw) {
            this.type = type;
            this.tw = tw;
        }


        public TW get() {
            return tw;
        }
    }

    private static class PairFacotry<T> {

        private <K> Pair<K, T> of(Class<K> type, T t) {
            return new Pair<K, T>(type, t);
        }
    }

    public <T, R> R of(T t, Function<? super T, ? extends R> mapping) {
        R apply = mapping.apply(t);
        return apply;
    }

    @Test
    public void test() {

        String[] strings = {"1", "2", "3"};
        Object[] objects = strings;
        System.out.println(objects);

    }

    public static class Some<T> {

        public void test(T t) {

            System.out.println(t);
        }

        @SuppressWarnings("unchecked")
        public <R> Some<R> toSome() {
            return (Some<R>) this;
        }
    }

    @Test
    public void test2() {
        Some<String> some = new Some<>();
        some.test("a123");

        Some<Integer> some1 = some.toSome();
        some1.test(123);

    }

}
