package com.leaderli.demo;

import javassist.CannotCompileException;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.FixedValue;
import net.sf.cglib.proxy.MethodInterceptor;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.assertEquals;

public class TestCglib {
    public static class HelloFather {
        public int print1() {

            System.out.println("print1 father");
            return 200;

        }
        public int print() {
            print1();

            System.out.println("print father");
            return 200;

        }
    }

    public static class Hello extends HelloFather {
        public int print1() {

            System.out.println("print1 son");
            return 200;

        }
        public int print() {
            print1();
            System.out.println("print son");
            return 100;

        }
    }

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException, CannotCompileException {


        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Hello.class);
        enhancer.setCallback(new Callback() {
        });
        enhancer.setCallback((MethodInterceptor) (obj, method, params, proxy) -> {


            return proxy.invokeSuper(obj, params);
//            return proxy.invoke(obj,args);
//            return method.invoke(obj, args);
//            return null;
        });
        HelloFather hello = (HelloFather) enhancer.create();
        hello.print();

        HelloFather.class.getMethod("print").invoke(hello);
    }
    public static class PersonService {
        public String sayHello(String name) {
            return "Hello " + name;
        }

        public Integer lengthOfName(String name) {
            return name.length();
        }
    }
    @Test
    public void test1() throws Throwable{

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(PersonService.class);
        enhancer.setCallback((FixedValue) () -> "Hello Tom!");
        PersonService proxy = (PersonService) enhancer.create();

        String res = proxy.sayHello(null);

        assertEquals("Hello Tom!", res);

    }
}
