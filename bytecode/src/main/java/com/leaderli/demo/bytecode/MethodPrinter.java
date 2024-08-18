package com.leaderli.demo.bytecode;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.util.Printer;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.objectweb.asm.Opcodes.*;


public class MethodPrinter {


    public static void main(String[] args) throws Exception {


        ClassReader classReader = new ClassReader(Person.class.getName());
        Map<String, MethodNode> methodVisitors = new HashMap<>();
        ClassVisitor classVisitor = new ClassVisitor(ASM6) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                String id = Modifier.toString(access) + " " + name + " " + desc + " " + signature + " " + Arrays.toString(exceptions);
                MethodNode methodNode = new MethodNode(ASM6, access, name, desc, signature, exceptions);
                methodVisitors.put(id, methodNode);
                return methodNode;
            }
        };
        classReader.accept(classVisitor, 0);
        methodVisitors.forEach((k, v) -> {
            System.out.println(k);
            v.instructions.iterator().forEachRemaining(node -> {
                int opcode = node.getOpcode();
                if (opcode > -1) {
                    System.out.println(Printer.OPCODES[opcode] + " " + opcode + " " + (opcode < INVOKEVIRTUAL || opcode > INVOKEDYNAMIC) + " " + node.getClass());
                }
            });
        });
    }
}
