package com.leaderli.demo;

import com.AssistDemo;
import javassist.*;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

public class TestJavaAssist {

    @Test
    public void test() throws CannotCompileException, NotFoundException, IOException, ClassNotFoundException {

        ClassPool classPool = ClassPool.getDefault();

        classPool.insertClassPath(new ClassClassPath(this.getClass()));

        CtClass ctClass = classPool.makeClass("hello");

//        ctClass.writeFile();
        ctClass.toClass();
        Class aClass = Class.forName("hello");


        ctClass.detach();

        ctClass = null;
        classPool = null;
        System.out.println(aClass + "@" + aClass.hashCode());
//        System.out.println(Arrays.toString(aClass.getMethods()));


    }

    @Test
    public void test1() throws Exception {
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.get("com.AssistDemo");

        CtField id = cc.getField("id");
        System.out.println(id);
        id.setName("newName");

        Class<?> aClass = cc.toClass();

        System.out.println(Arrays.toString(aClass.getDeclaredFields()));

        System.out.println(Arrays.toString(AssistDemo.class.getDeclaredFields()));


//        cc.setSuperclass(pool.get("test.Point"));
//        cc.writeFile();

    }

    @Test
    public void test3() throws Exception {
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.get("com.AssistDemo");

        Loader cl = new Loader(pool);
        Class<?> aClass = cl.loadClass("com.AssistDemo");
        Object o = aClass.newInstance();
        System.out.println(aClass + "@" + aClass.hashCode() + " " + aClass.getClassLoader());
        aClass = AssistDemo.class;
        System.out.println(aClass + "@" + aClass.hashCode() + " " + aClass.getClassLoader());
    }

    @Test
    public void test4() throws Throwable {

        ClassPool pool = ClassPool.getDefault();
        Loader cl = new Loader(pool);
        String name = "com.AssistDemo";
        cl.addTranslator(pool, new MyTranslator());
        System.out.println(Arrays.toString(cl.loadClass(name).getDeclaredFields()));

    }

    static class MyTranslator implements Translator {

        @Override
        public void start(ClassPool classPool) throws NotFoundException, CannotCompileException {

            System.out.println("start:" + classPool);
        }

        @Override
        public void onLoad(ClassPool classPool, String name) throws NotFoundException, CannotCompileException {
            System.out.println("onload:" + classPool + " " + name);

            CtClass ctClass = classPool.get(name);
            System.out.println(Arrays.toString(ctClass.toClass().getDeclaredFields()));

            ctClass.defrost();
            ctClass.getField("id").setName("fuck");

        }
    }


    @Test
    public void test5() throws Exception {
        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.get("com.AssistDemo");
        CtField id = ctClass.getField("id");
        CodeConverter codeConverter = new CodeConverter();
        codeConverter.redirectFieldAccess(id, ctClass, "fuck");
        id.setName("fuck");
        ctClass.instrument(codeConverter);
        Runnable run = (Runnable) ctClass.toClass().newInstance();
        run.run();


    }

    @Test
    public void test8() throws Throwable {
        ClassPool pool = ClassPool.getDefault();

        CtClass ct = pool.makeClass("Hello");
        //生成属性
        CtField id = CtField.make("public Integer id = new Integer(1);", ct);
        ct.addField(id);

        Class clazz = ct.toClass();
        Object instance = clazz.newInstance();
        System.out.println(clazz.getField("id").get(instance));


    }
}
