package io.leaderli.demo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.function.Consumer;

public class AopTest {


    static <T> T plugin(Class<T> _interface, Object... args) {

        if (_interface.isInterface()) {
            InvocationHandler invocationHandler = (proxy, method, params) -> null;
            return _interface.cast(Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[]{_interface}, invocationHandler));
        }
        throw new IllegalArgumentException("only support interface plugin");

    }


    public static void main(String[] args) {

        Consumer plugin = plugin(Consumer.class);
        plugin.accept("hello");
    }
}
