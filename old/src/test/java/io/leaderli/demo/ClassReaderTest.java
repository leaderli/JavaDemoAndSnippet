package io.leaderli.demo;

import io.leaderli.litool.core.resource.ResourceUtil;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.*;
import org.objectweb.asm.tree.MethodNode;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClassReaderTest {

    @Test
    void test() throws IOException {
        InputStream resourceAsStream = ResourceUtil.getResourceAsStream("ResourceUtil.class", ResourceUtil.class);
        ClassReader classReader = new ClassReader(resourceAsStream);
        List<MethodNode> methodNodes = new ArrayList<>();
        classReader.accept(new ClassVisitor(Opcodes.ASM5) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                String m = name;
                return new MethodVisitor(Opcodes.ASM5) {
                    @Override
                    public void visitInvokeDynamicInsn(String name, String desc, Handle bsm, Object... bsmArgs) {
                        System.out.println(m + " " + name + " " + Arrays.toString(bsmArgs));
                        super.visitInvokeDynamicInsn(name, desc, bsm, bsmArgs);
                    }

                    @Override
                    public void visitParameter(String name, int access) {
                        super.visitParameter(name, access);
                    }

                    @Override
                    public AnnotationVisitor visitAnnotationDefault() {
                        return super.visitAnnotationDefault();
                    }

                    @Override
                    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
                        return super.visitAnnotation(desc, visible);
                    }

                    @Override
                    public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
                        return super.visitTypeAnnotation(typeRef, typePath, desc, visible);
                    }

                    @Override
                    public AnnotationVisitor visitParameterAnnotation(int parameter, String desc, boolean visible) {
                        return super.visitParameterAnnotation(parameter, desc, visible);
                    }

                    @Override
                    public void visitAttribute(Attribute attr) {
                        super.visitAttribute(attr);
                    }

                    @Override
                    public void visitCode() {
                        super.visitCode();
                    }

                    @Override
                    public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
                        super.visitFrame(type, nLocal, local, nStack, stack);
                    }

                    @Override
                    public void visitInsn(int opcode) {
                        super.visitInsn(opcode);
                    }

                    @Override
                    public void visitIntInsn(int opcode, int operand) {
                        super.visitIntInsn(opcode, operand);
                    }

                    @Override
                    public void visitVarInsn(int opcode, int var) {
                        super.visitVarInsn(opcode, var);
                    }

                    @Override
                    public void visitTypeInsn(int opcode, String type) {
                        super.visitTypeInsn(opcode, type);
                    }

                    @Override
                    public void visitFieldInsn(int opcode, String owner, String name, String desc) {
                        super.visitFieldInsn(opcode, owner, name, desc);
                    }

                                 @Override
                    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
                        System.out.println(m + " " + name + " " +owner);

                        super.visitMethodInsn(opcode, owner, name, desc, itf);
                    }

                    @Override
                    public void visitJumpInsn(int opcode, Label label) {
                        super.visitJumpInsn(opcode, label);
                    }

                    @Override
                    public void visitLabel(Label label) {
                        super.visitLabel(label);
                    }

                    @Override
                    public void visitLdcInsn(Object cst) {
                        super.visitLdcInsn(cst);
                    }

                    @Override
                    public void visitIincInsn(int var, int increment) {
                        super.visitIincInsn(var, increment);
                    }

                    @Override
                    public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
                        super.visitTableSwitchInsn(min, max, dflt, labels);
                    }

                    @Override
                    public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
                        super.visitLookupSwitchInsn(dflt, keys, labels);
                    }

                    @Override
                    public void visitMultiANewArrayInsn(String desc, int dims) {
                        super.visitMultiANewArrayInsn(desc, dims);
                    }

                    @Override
                    public AnnotationVisitor visitInsnAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
                        return super.visitInsnAnnotation(typeRef, typePath, desc, visible);
                    }

                    @Override
                    public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
                        super.visitTryCatchBlock(start, end, handler, type);
                    }

                    @Override
                    public AnnotationVisitor visitTryCatchAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
                        return super.visitTryCatchAnnotation(typeRef, typePath, desc, visible);
                    }

                    @Override
                    public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
                        super.visitLocalVariable(name, desc, signature, start, end, index);
                    }

                    @Override
                    public AnnotationVisitor visitLocalVariableAnnotation(int typeRef, TypePath typePath, Label[] start, Label[] end, int[] index, String desc, boolean visible) {
                        return super.visitLocalVariableAnnotation(typeRef, typePath, start, end, index, desc, visible);
                    }

                    @Override
                    public void visitLineNumber(int line, Label start) {
                        super.visitLineNumber(line, start);
                    }

                    @Override
                    public void visitMaxs(int maxStack, int maxLocals) {
                        super.visitMaxs(maxStack, maxLocals);
                    }

                    @Override
                    public void visitEnd() {
                        super.visitEnd();
                    }
                };
            }
        }, 0);
    }
}
