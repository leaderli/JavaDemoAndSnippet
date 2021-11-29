package com.leaderli.demo.demo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class AopTest {

    static <T> T beanInstance(Class<T> _interface, Object ... args) {
        //...
        return null;
    }

    static <T> T plugin(Class<T> _interface, Object ... args) {

        if (_interface.isInterface()) {
            T target = beanInstance(_interface, args);

            InvocationHandler invocationHandler = null;
            if (target == null) {
                invocationHandler = (proxy, method, params) -> null;
            } else {
                invocationHandler = (proxy, method, params) -> method.invoke(target, params);

            }
            return _interface.cast(Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[]{_interface}, invocationHandler));
        }
        throw new IllegalArgumentException("only support interface plugin");

    }

    private interface InterfaceClass {

        void test(String para);
    }

    public static void main(String[] args) {

        InterfaceClass plugin = plugin(InterfaceClass.class);
        plugin.test("hello");
    }
}
