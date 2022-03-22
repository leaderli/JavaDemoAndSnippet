package com.leaderli.demo;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import spoon.Launcher;
import spoon.SpoonAPI;
import spoon.reflect.CtModel;
import spoon.reflect.code.CtComment;
import spoon.reflect.code.CtFieldRead;
import spoon.reflect.declaration.*;
import spoon.reflect.factory.Factory;
import spoon.reflect.reference.CtFieldReference;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.CtBFSIterator;
import spoon.reflect.visitor.filter.AbstractFilter;
import spoon.reflect.visitor.filter.TypeFilter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


public class TestSpoon {

    @Test
    public void test() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        CtClass<?> l = Launcher.parseClass("public class A { public void m() { System.out.println(\"yeah\");} }");

        l.setLabel("fuck");
        System.out.println(l.toStringWithImports());
        Object o = l.newInstance();
        Method m = o.getClass().getDeclaredMethod("m");
        m.invoke(o);

        System.out.println(o.getClass().getClassLoader());
        System.out.println(o.getClass().getClassLoader().getParent());
        System.out.println(this.getClass().getClassLoader());

    }

    @Test
    public void noTreeSet() throws Exception {
        SpoonAPI spoon = new Launcher();
        spoon.addInputResource("src/test/java/");
        spoon.buildModel();

        assertEquals(0, spoon.getFactory().Package().getRootPackage().getElements(new AbstractFilter<CtComment>() {
            @Override
            public boolean matches(CtComment element) {
                System.out.println("-----------------------------------------------------------");
                System.out.println(element);
//                return element.getType().getTypeDeclaration().getActualClass().equals(AsciiTable.class);
//                return element.getType().getTypeDeclaration().getActualClass().equals(AsciiTable.class);
                return element.isImplicit();

            }

        }).size());
    }

    @Test
    public void testGoodTestClassNames() {
        SpoonAPI spoon = new Launcher();
        spoon.addInputResource("src/test/java/");
        spoon.buildModel();

        List<CtMethod<?>> methods = spoon.getModel().getRootPackage().getElements(new TypeFilter<CtMethod<?>>(CtMethod.class) {
            @Override
            public boolean matches(CtMethod<?> element) {
                return super.matches(element) && element.getAnnotation(Test.class) != null;
            }
        });
        for (CtMethod<?> ctMethod : methods) {
            String simpleName = ctMethod.getParent(CtClass.class).getSimpleName();
            assertTrue(simpleName.startsWith("Test") || simpleName.endsWith("Test"), "naming contract violated for " + simpleName);
        }
    }

    @Test
    public void testDocumentation() {
        SpoonAPI spoon = new Launcher();
        spoon.addInputResource("src/test/java/");
        spoon.buildModel();
        List<String> notDocumented = new ArrayList<>();
        for (CtMethod<?> method : spoon.getModel().getElements(new TypeFilter<>(CtMethod.class))) {
            if (method.hasModifier(ModifierKind.PUBLIC) && method.getTopDefinitions().size() == 0) {
                if (method.getDocComment().length() < 20) { // at least 20 characters
                    notDocumented.add(method.getParent(CtType.class).getQualifiedName() + "#" + method.getSignature());
                }
            }
        }
        if (notDocumented.size() > 0) {
            fail(notDocumented.size() + " public methods should be documented with proper API documentation: \n" + StringUtils.join(notDocumented, "\n"));
        }
    }

    @Test
    public void test4() {
        SpoonAPI spoon = new Launcher();
        spoon.addInputResource("src/test/java/");
        CtModel model = spoon.buildModel();
        // 查找所有成员变量
        List<CtField<?>> fields = model.getElements(new TypeFilter<>(CtField.class));
        // 移除非私有成员变量，移除序列化成员变量
        fields.removeIf(v -> !v.isPrivate());
        fields.removeIf(v -> v.getSimpleName().equals("serialVersionUID"));
        // Step 4: Query the model for all fieldReads
        // 查找所有使用成员变量的模型
        List<CtFieldRead<?>> fieldRead = model.getElements(new TypeFilter<>(CtFieldRead.class));
        // 移除没有变量声明的模型
        fieldRead.removeIf(v -> v.getVariable().getFieldDeclaration() == null);
        // 记录被使用的成员变量
        HashSet<CtField<?>> lookUp = fieldRead.stream()
                .map(CtFieldRead::getVariable)
                .map(CtFieldReference::getFieldDeclaration)
                .collect(Collectors.toCollection(HashSet::new));
        // 查找未被使用的成员变量
        List<CtField<?>> fieldsWithRead = fields.stream()
                // 	 every field must have a read
                .filter(field -> !lookUp.contains(field))
                .collect(Collectors.toList());
        assertEquals("Some Fields have no read/write", Collections.emptyList(), (Supplier<String>) fields);

    }

    @Test
    public void test41() {
        SpoonAPI spoon = new Launcher();
        spoon.getEnvironment().setAutoImports(true);
        spoon.addInputResource("src/test/java/");
        CtModel model = spoon.buildModel();
        Collection<CtType<?>> allTypes = model.getAllTypes();
        CtType<?> first = allTypes.stream().findFirst().get();
//        System.out.println(first);
//        System.out.println(first.getMethods());
//
//        Object valueByRole = first.getValueByRole(CtRole.METHOD);
//        System.out.println(valueByRole);
//

        List<CtElement> directChildren = first.getDirectChildren();

        for (CtElement directChild : directChildren) {
            System.out.println(directChild);
        }

    }

    @Test
    public void test5() {


        SpoonAPI launcher = new Launcher();
        launcher.addInputResource("src/test/java/");
        CtModel model = launcher.buildModel();

        CtBFSIterator iterator = new CtBFSIterator(model.getAllTypes().stream().findFirst().get());

        while (iterator.hasNext()) {
            CtElement next = iterator.next();
            System.out.println(next.getPath().toString());
            System.out.println(next.getPosition());
        }

    }

    @Test
    public void test6() {
        SpoonAPI spoon = new Launcher();
        spoon.getEnvironment().setAutoImports(true);
        spoon.addInputResource("src/test/java");
        CtModel ctModel = spoon.buildModel();

        Factory factory = ctModel.getRootPackage().getFactory();
        CtPackage aPackage = factory.createPackage();
        aPackage.setSimpleName("fuck");

        CtClass newClass = aPackage.getFactory().createClass();
        CtNamedElement tacos = newClass.setSimpleName("Tacos");
        System.out.println(newClass);

        CtTypeReference<Date> dateRef = factory.createCtTypeReference(Date.class);
        CtTypeReference<List<Date>> listRef = factory.createCtTypeReference(List.class);

        listRef.addActualTypeArgument(dateRef);


        CtField<List<Date>> listOfDates = factory.createField();
        listOfDates.setSimpleName("dates");
        listOfDates.setType(listRef);
        listOfDates.addModifier(ModifierKind.PRIVATE);

        newClass.addField(listOfDates);

        System.out.println(newClass.toStringWithImports());

        Object o = newClass.newInstance();
        System.out.println(o);
    }

    @Test
    public void test7() {

        Launcher launcher = new Launcher();
        launcher.getEnvironment().setAutoImports(true);
        Factory factory = launcher.getFactory();
        CtClass<Object> fuck = factory.Class().create("Fuck");
        fuck.addModifier(ModifierKind.PUBLIC);

        CtTypeReference<Date> dateRef = factory.Code().createCtTypeReference(Date.class);
        CtTypeReference<List<Date>> listRef = factory.Code().createCtTypeReference(List.class);

        listRef.addActualTypeArgument(dateRef);


        CtField<List<Date>> listOfDates = factory.createField();
        listOfDates.setSimpleName("dates");
        listOfDates.setType(listRef);
        listOfDates.addModifier(ModifierKind.PRIVATE);

        fuck.addField(listOfDates);


        System.out.println(fuck.toStringWithImports());
        Object o = fuck.newInstance();

        launcher.setSourceOutputDirectory("fuck123123");
        launcher.prettyprint();


    }

    @Test
    public void test9() {

        CtClass<?> ctClass = Launcher.parseClass("package fuck;public class Fuck {}");
        System.out.println(ctClass.toStringWithImports());
    }
}
