package io.leaderli.demo;


import com.github.javaparser.JavaParser;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.printer.YamlPrinter;
import com.github.javaparser.utils.SourceRoot;
import io.leaderli.demo.bean.Person;
import io.leaderli.litool.core.meta.Lira;
import io.leaderli.litool.core.util.ConsoleUtil;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class TestJavaParser {
    MethodDeclaration m1;
    NameExpr parser = null;

    String m1() {
        parse("fuck");
        return "123";
    }

    void parse(String fuck) {

    }

    @Test
    public void test2() throws FileNotFoundException {

        JavaParser javaParser = new JavaParser();
        File file = new File("src/test/java/io/leaderli/demo/TestJavaParser.java");
        Optional<CompilationUnit> result = javaParser.parse(file).getResult();

        if (!result.isPresent()) {
            return;
        }

        CompilationUnit cu = result.get();
        List<Integer> returns = new ArrayList<>();
        new VoidVisitorAdapter<Object>() {
            @Override
            public void visit(MethodCallExpr n, Object arg) {

                NodeList<Expression> arguments = n.getArguments();


                if (!n.getName().toString().equals("parse") || arguments.size() != 1) {

                    return;
                }

                Expression expression = Lira.of(arguments).first().get();
                if (expression.isStringLiteralExpr()) {
                    parser = (NameExpr) n.getChildNodes().get(0);
                }
            }
        }.visit(cu, null);
        new VoidVisitorAdapter<Object>() {
            @Override
            public void visit(VariableDeclarationExpr n, Object arg) {

//                System.out.println(n);
            }

            @Override
            public void visit(FieldDeclaration n, Object arg) {

                if (n.getChildNodes().get(0).equals(parser)) {
                    System.out.println(n);
                }
            }
        }.visit(cu, null);

    }

    @Test
    public void test() throws FileNotFoundException {


        JavaParser javaParser = new JavaParser();
        File file = new File("src/test/java/io/leaderli/demo/TestJavaParser.java");
        Optional<CompilationUnit> result = javaParser.parse(file).getResult();

        if (!result.isPresent()) {
            return;
        }

        CompilationUnit cu = result.get();
        List<Integer> returns = new ArrayList<>();
        new VoidVisitorAdapter<Object>() {
            @Override
            public void visit(MethodCallExpr n, Object arg) {

                NodeList<Expression> arguments = n.getArguments();


                if (arguments.size() == 1) {

                    Expression expression = Lira.of(arguments).first().get();
                    if (expression.isStringLiteralExpr()) {
                        System.out.println(n.getName() + " " + expression);
                        parser = (NameExpr) n.getChildNodes().get(0);
                    }
                }
            }
        }.visit(cu, null);
        new VoidVisitorAdapter<Object>() {
            @Override
            public void visit(MethodDeclaration n, Object arg) {
                if ("m1".equals(n.getName().toString())) {
                    m1 = n;
                }
            }
        }.visit(cu, null);

        new VoidVisitorAdapter<Object>() {

            @Override
            public void visit(ReturnStmt returnStmt, Object arg) {
                //打印return语法节点
                System.out.println("ReturnStmt:" + returnStmt);
                for (Node childNode : returnStmt.getChildNodes()) {
                    if (childNode instanceof NameExpr) {
                        //返回语法树使用了变量(成员变量和局部变量，不包括使用this引用的),则查找变量声明和赋值的地方
                        String local = ((NameExpr) childNode).getName().asString();

                        new VoidVisitorAdapter<Object>() {

                            @Override
                            public void visit(FieldDeclaration fieldDeclaration, Object arg) {
                                System.out.println("[" + returnStmt + "] FieldDeclaration:" + fieldDeclaration);
                                new VoidVisitorAdapter<Object>() {
                                    @Override
                                    public void visit(VariableDeclarator variableDeclarator, Object arg) {
                                        boolean find = false;
                                        for (Node node : variableDeclarator.getChildNodes()) {
                                            if (node instanceof SimpleName) {
                                                //是否为当前需要查找找到指定的变量声明名称
                                                find = (local.equals(((SimpleName) node).asString()));
                                            } else {
                                                if (find && node instanceof IntegerLiteralExpr) {
                                                    //若是则找到变量声明赋值的字面量
                                                    int value = ((IntegerLiteralExpr) node).asNumber().intValue();
                                                    returns.add(value);
                                                }
                                            }

                                        }
                                    }
                                }.visit(fieldDeclaration, null);
                            }

                            @Override
                            public void visit(VariableDeclarationExpr variableDeclarationExpr, Object arg) {

                                //变量声明
                                System.out.println("[" + returnStmt + "] VariableDeclarationExpr:" + variableDeclarationExpr);

                                new VoidVisitorAdapter<Object>() {
                                    @Override
                                    public void visit(VariableDeclarator variableDeclarator, Object arg) {
                                        boolean find = false;
                                        for (Node node : variableDeclarator.getChildNodes()) {
                                            if (node instanceof SimpleName) {
                                                //是否为当前需要查找找到指定的变量声明名称
                                                find = (local.equals(((SimpleName) node).asString()));
                                            } else {
                                                if (find && node instanceof IntegerLiteralExpr) {
                                                    //若是则找到变量声明赋值的字面量
                                                    int value = ((IntegerLiteralExpr) node).asNumber().intValue();
                                                    returns.add(value);
                                                }
                                            }

                                        }
                                    }
                                }.visit(variableDeclarationExpr, null);
                            }

                            @Override
                            public void visit(AssignExpr assignExpr, Object arg) {
                                //变量赋值
                                System.out.println("[" + local + "]" + assignExpr);

                                boolean find = false;
                                for (Node node : assignExpr.getChildNodes()) {

                                    if (node instanceof NameExpr) {
                                        //是否为当前需要查找找到指定的变量赋值
                                        find = (local.equals(((NameExpr) node).getNameAsString()));
                                    } else {
                                        if (find && node instanceof IntegerLiteralExpr) {
                                            //若是则找到变量赋值的字面量
                                            int value = ((IntegerLiteralExpr) node).asNumber().intValue();
                                            returns.add(value);
                                        }
                                    }
                                }

                            }
                        }.visit(cu, null);


                    } else if (childNode instanceof IntegerLiteralExpr) {
                        //返回语法树直接返回字面量
                        int value = ((IntegerLiteralExpr) childNode).asNumber().intValue();
                        returns.add(value);


                    } else if (childNode instanceof FieldAccessExpr) {
                        //返回语法树使用了this取引用的变量，略

                    }
                }

            }
        }.visit(m1, null);

        System.out.println(returns);
    }

    @Test
    public void test1() throws FileNotFoundException, URISyntaxException {

        URI uri = Objects.requireNonNull(TestJavaParser.class.getResource("/")).toURI();
        Path path = Paths.get(uri).getParent().getParent();
        SourceRoot sourceRoot = new SourceRoot(path);
        CompilationUnit cu = sourceRoot.parse("src/test/java/com/leaderli/litest", "MyTest.java");
        //删除import
        cu.walk(ImportDeclaration.class, e -> {
            if (e.isStatic() && "org.junit.Assert.assertEquals".equals(e.getNameAsString())) {
                e.remove();
            }
        });


        //删除方法调用
        cu.walk(MethodCallExpr.class, e -> {
            e.getParentNode().get().remove();
        });
        Statement statement = StaticJavaParser.parseStatement("assert a.equals(\"123\");");
        cu.walk(MethodDeclaration.class, e -> {
            e.getBody().get().addStatement(statement);

        });
        System.out.println(cu);
        sourceRoot.saveAll();
    }

    @Test
    void test4() throws URISyntaxException, IOException {
        URI uri = Objects.requireNonNull(TestJavaParser.class.getResource("/")).toURI();
        Path path = Paths.get(uri).getParent().getParent();
        SourceRoot sourceRoot = new SourceRoot(path);
        CompilationUnit cu = sourceRoot.parse("src/test/java/io/leaderli/demo/ast", "PersonService.java");
        FileWriter fileWriter = new FileWriter("ast.yml");
        YamlPrinter printer = new YamlPrinter(true);
//        DotPrinter printer = new DotPrinter(true);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.print(printer.output(cu));
        printWriter.flush();

    }

    @Test
    void test5() throws URISyntaxException {

        URI uri = Objects.requireNonNull(TestJavaParser.class.getResource("/")).toURI();
        System.out.println(Paths.get(uri));
        Path path = Paths.get(uri).getParent().getParent();
        SourceRoot sourceRoot = new SourceRoot(path);
        CompilationUnit cu = sourceRoot.parse("src/test/java/io/leaderli/demo/ast", "PersonService.java");

        for (VariableDeclarator variableDeclarator : cu.findAll(VariableDeclarator.class)) {

            ConsoleUtil.print(variableDeclarator, variableDeclarator.getType());
        }
        String name = Person.class.getName();
        System.out.println(cu.findAll(ImportDeclaration.class, im -> {
            String importName = im.getNameAsString();
            System.out.println(name + " " + importName);
            return importName.equals(name) || im.isAsterisk() && name.startsWith(importName);
        }));
    }
}

