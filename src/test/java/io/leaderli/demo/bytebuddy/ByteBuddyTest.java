package io.leaderli.demo.bytebuddy;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;
import org.junit.jupiter.api.Test;

import java.lang.instrument.ClassDefinition;
import java.util.function.Supplier;

public class ByteBuddyTest {
    public static void test() {

        System.out.println(new Object() {
        }.getClass().getEnclosingMethod());
        System.out.println("fuck");
    }

    public static void main(String[] args) throws Throwable {
        mock(ByteDemo.class, ByteBuddyTest.class);
        ByteDemo.test();
    }
//        ClassLoader classLoader = ByteBuddyTest.class.getClassLoader();
//
//        ClassPool pool = ClassPool.getDefault();
//        String name = ByteDemo.class.getName();
//        CtClass cc = pool.get(name);
////        CtClass cc = pool.get("io.leaderli.demo.bytebuddy.ByteDemo");
//        CtMethod test = pool.getMethod(ByteBuddyTest.class.getName(), "test");
//        cc.getClassInitializer().setBody("{}");
//        cc.getMethod("test", "()V").setBody(test, null);
//        ClassDefinition definition = new ClassDefinition(ByteDemo.class, cc.toBytecode());
//        RedefineClassAgent.redefineClasses(definition);
//
//
//
//        ByteDemo.test();


    public static void mock(Class<?> clazz, Class<?> delegate) {
        try {
            ClassPool pool = ClassPool.getDefault();
            CtClass cc = pool.get(clazz.getName());
            CtClass ccd = pool.get(delegate.getName());
//        CtClass cc = pool.get("io.leaderli.demo.bytebuddy.ByteDemo");
            cc.getClassInitializer().setBody("{}");
            for (CtMethod method : cc.getMethods()) {
                CtMethod methodd = ccd.getMethod(method.getName(), method.getMethodInfo().getDescriptor());
                if (method != null) {
                    method.setBody(methodd, null);
                }
            }
            ClassDefinition definition = new ClassDefinition(clazz, cc.toBytecode());
            ByteBuddyAgent.getInstrumentation().redefineClasses(definition);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void testSub() throws InstantiationException, IllegalAccessException {
        // 定义一个父类

        // 使用ByteBuddy创建子类
        DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
                .subclass(Supplier.class)
                .method(ElementMatchers.named("get"))
                .intercept(FixedValue.value("Hello, ByteBuddy!"))
                .make();

        // 加载子类并实例化
        Class<?> subClass = dynamicType.load(Supplier.class.getClassLoader())
                .getLoaded();
        Supplier foo = (Supplier) subClass.newInstance();

        // 调用子类的方法
        System.out.println(foo.get()); // 输出: Hello, ByteBuddy!
    }

}
