package com.leaderli.demo;

import javassist.*;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

public class TestClassLoader {

    @Test
    public void test() throws ClassNotFoundException, InstantiationException, IllegalAccessException {

        MyClassLoader myClassLoader = new MyClassLoader();
        Class<?> clazz = myClassLoader.loadClass("com.AssistDemo1");
        Object o = clazz.newInstance();

//        AssistDemo  assistDemo = (AssistDemo) o;
        System.out.println(Arrays.toString(clazz.getDeclaredFields()));


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
        } catch (NotFoundException | CannotCompileException | IOException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return super.findClass(name);
    }
}
