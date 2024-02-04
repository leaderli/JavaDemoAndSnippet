package io.leaderli.demo.bytebuddy;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.lang.instrument.ClassDefinition;

public class ByteBuddyTest {
    public static void test() {

        System.out.println(new Object() {
        }.getClass().getEnclosingMethod());
        System.out.println("fuck");
    }

    public static void main(String[] args) throws Throwable {
        ClassLoader classLoader = ByteBuddyTest.class.getClassLoader();

        ClassPool pool = ClassPool.getDefault();
        String name = ByteDemo.class.getName();
        CtClass cc = pool.get(name);
//        CtClass cc = pool.get("io.leaderli.demo.bytebuddy.ByteDemo");
        CtMethod test = pool.getMethod(ByteBuddyTest.class.getName(), "test");
        cc.getClassInitializer().setBody("{}");
        cc.getMethod("test", "()V").setBody(test, null);
        ClassDefinition definition = new ClassDefinition(ByteDemo.class, cc.toBytecode());
        RedefineClassAgent.redefineClasses(definition);



        ByteDemo.test();
    }
}
