package io.leaderli.demo;

import org.junit.jupiter.api.Test;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;

public class AopTest {


    static <T> T plugin(Class<T> _interface, T t) {

        if (_interface.isInterface()) {
            InvocationHandler invocationHandler = (proxy, method, params) -> method.invoke(t, params);
            return _interface.cast(Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[]{_interface}, invocationHandler));
        }
        throw new IllegalArgumentException("only support interface plugin");

    }

    class  A implements  Consumer<String>{

        @Override
        public void accept(String s) {

        }
    }
    class Record
    {
        public Record(String blah) { }
        public Record(Integer blah) { }
    }
    @Test
    public void test() {

        System.out.println(Arrays.toString(Consumer.class.getTypeParameters()).getClass());

        Consumer<String> consumer = s -> {

        };


        System.out.println(Arrays.toString(consumer.getClass().getTypeParameters()));
        System.out.println(consumer.getClass().getComponentType());


    }

    public static void main(String[] args) {

        Consumer<String> plugin = plugin(Consumer.class, System.out::println);

        plugin.accept("hello");

        Function<String,Integer> plugin2 = plugin(Function.class, i -> {
            System.out.println(i);
            return i;
        });
        plugin2.apply("hello");
    }
}
