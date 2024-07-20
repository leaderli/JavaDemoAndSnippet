package io.leaderli.demo.ssist;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import net.bytebuddy.agent.ByteBuddyAgent;
import org.junit.jupiter.api.Test;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;


public class TestAssist {

    @Test
    void test() throws Exception {


        // Create a ClassPool
        ClassPool classPool = ClassPool.getDefault();

        // Get the target class
        CtClass ctClass = classPool.get("io.leaderli.demo.ssist.Bean");

        // Get the target method
        CtMethod ctMethod = ctClass.getDeclaredMethod("m1");

        // Instrument method calls
        ctMethod.instrument(new ExprEditor() {
            @Override
            public void edit(MethodCall m) throws CannotCompileException {
                super.edit(m);

                // Get the method name being called
                String methodName = m.getMethodName();

                // Get the method parameters


                // Prepare the code to be inserted before and after the method call
                StringBuilder code = new StringBuilder();
                code.append("{\n");
                code.append("System.out.println(\"Before method call:\");\n");
                code.append("$proceed($$);\n"); // Call the original method
                code.append("System.out.println(\"After method call:  => \" + ($w)$_);\n");
                code.append("}\n");

                System.out.println(m);
                // Replace the method call with the new code
                m.replace(code.toString());

            }
        });

        ctMethod.insertAfter("System.err.println( java.util.Arrays.toString($args));");
        ctMethod.insertAfter("System.err.println( $_ );");
        ctMethod.instrument(new ExprEditor() {
            @Override
            public void edit(MethodCall m) throws CannotCompileException {
                super.edit(m);
                System.out.println(m.getMethodName() + " " + m.getLineNumber());
            }


        });

        // Save the modified class
        System.out.println(ctClass.toClass());

        System.out.println(new Bean().m1(1, "a"));

    }

    static class A {

        public static int m1() {
            return 1;
        }

        public static int m2() {
            return 2;
        }
    }

    Instrumentation instrumentation = ByteBuddyAgent.install();
    @Test
    void testDefine() throws Throwable {
        ClassPool classPool = ClassPool.getDefault();
        CtClass ct = classPool.getCtClass(A.class.getName());
        ct.getDeclaredMethod("m1").setBody("{return 10;}");
        byte[] bytecode = ct.toBytecode();
        instrumentation.redefineClasses(new ClassDefinition(A.class,bytecode));
        ct.defrost();
        System.out.println(A.m1());
        System.out.println(A.m2());
        ct.detach();
        ct = classPool.getCtClass(A.class.getName());
        ct.toBytecode();
        ct.defrost();
        ct = classPool.getCtClass(A.class.getName());
        ct.detach();
        ct.getDeclaredMethod("m2").setBody("{return 20;}");
        bytecode = ct.toBytecode();
        instrumentation.redefineClasses(new ClassDefinition(A.class,bytecode));
        ct.defrost();
        System.out.println(A.m1());
        System.out.println(A.m2());

    }

}
