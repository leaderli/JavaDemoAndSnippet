package io.leaderli.demo;

import io.leaderli.demo.bean.Person;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.util.Printer;

import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.objectweb.asm.Opcodes.*;


/**
 * @author leaderli
 * @since 2022/3/22
 */
public class ASMTest {


    public static void main(String[] args) throws Exception {

//        ClassLoader classLoader = ASMTest.class.getClassLoader();
//        Class<?> fuck;
//        try (ByteClassLoader byteClassLoader = new ByteClassLoader(new URL[]{}, classLoader)) {
//            fuck = byteClassLoader.findClass("HelloWorld");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        System.out.println("class:" + fuck.getName());
//        for (Method method : fuck.getMethods()) {
//            if (method.getName().startsWith("main"))
//                method.invoke(null);
//        }

        ClassReader classReader = new ClassReader(Person.class.getName());
        Map<String, MethodNode> methodVisitors = new HashMap<>();
        ClassVisitor classVisitor = new ClassVisitor(ASM6) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                String id = Modifier.toString(access) + " " + name + " " + desc + " " + signature + " " + Arrays.toString(exceptions);
                MethodNode methodNode = new MethodNode(ASM6,access,name,desc,signature,exceptions) ;
                methodVisitors.put(id,methodNode);
                return methodNode;
            }
        };
        classReader.accept(classVisitor, 0);
        methodVisitors.forEach((k, v) -> {
            System.out.println(k);
            v.instructions.iterator().forEachRemaining(node->{
                int opcode = node.getOpcode();
                if(opcode >-1){
                    System.out.println(Printer.OPCODES[opcode]+" "+ opcode + " "+(opcode < INVOKEVIRTUAL || opcode > INVOKEDYNAMIC)+" "+node.getClass());
                }
            });
        });
    }

    ClassWriter cw = new ClassWriter(0);

    public byte[] serializeToBytes(String outputClazzName) {

        cw.visit(V1_8, ACC_PUBLIC + ACC_SUPER, outputClazzName, null, "java/lang/Object", null);
        addStandardConstructor();
        addMainMethod();
        cw.visitEnd();
        return cw.toByteArray();
    }

    private void addMainMethod() {

        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "main", "()V", null, null);
        mv.visitCode();
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn("Hello World! ");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        mv.visitInsn(RETURN);
        mv.visitMaxs(2, 0);
        mv.visitEnd();
    }

    private void addStandardConstructor() {

        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        mv.visitInsn(RETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();
    }
}

class ByteClassLoader extends URLClassLoader {
    static byte[] classBytes;

    public ByteClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    @Override
    protected Class<?> findClass(final String name) throws ClassNotFoundException {
        classBytes = new ASMTest().serializeToBytes(name);
        if (classBytes != null) {
            return defineClass(name, classBytes, 0, classBytes.length);
        }
        return super.findClass(name);
    }

}
