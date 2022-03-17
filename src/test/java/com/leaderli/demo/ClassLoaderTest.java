package com.leaderli.demo;

import javassist.*;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

public class ClassLoaderTest {

    @Test
    public void test() throws ClassNotFoundException, InstantiationException, IllegalAccessException {

        MyClassLoader myClassLoader = new MyClassLoader();
        Class<?> clazz = myClassLoader.loadClass("com.AssistDemo1");
        Object o = clazz.newInstance();

//        AssistDemo  assistDemo = (AssistDemo) o;
        System.out.println(Arrays.toString(clazz.getDeclaredFields()));


    }
    @Test
    public void test1() throws Throwable{

        URL resource = ClassLoaderTest.class.getResource("/");

        System.out.println(resource);

    }
}


class MyClassLoader extends ClassLoader {


    ClassPool pool = ClassPool.getDefault();

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        try {
            CtClass cc = pool.get(name);
            CtField id = cc.getField("id");
            id.setName("fuck");

            byte[] bytes = cc.toBytecode();
            System.out.println(cc.toClass().newInstance());
            return this.defineClass(name, bytes, 0, bytes.length);
        } catch (NotFoundException | CannotCompileException | IOException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return super.findClass(name);
    }
}
