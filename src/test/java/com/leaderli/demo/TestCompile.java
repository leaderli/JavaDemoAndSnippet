package com.leaderli.demo;

import org.junit.Test;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class TestCompile {


    public static int a = 1;

    @Test
    public void test() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String source = "package test;     " +
                "public class Test implements Runnable{\n" +
                "        @Override\n" +
                "        public void run() {\n" +
                "            System.out.println(\"test compile\");\n" +
                "        }\n" +
                " }";

// Save source in .java file.
        File root = new File("/java"); // On Windows running on C:\, this is C:\java.
        File sourceFile = new File(root, "test/Test.java");
        sourceFile.getParentFile().mkdirs();
        Files.write(sourceFile.toPath(), source.getBytes(StandardCharsets.UTF_8));

// Compile source file.
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        System.out.println(compiler);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        compiler.run(null, out, null, sourceFile.getPath());
        System.out.println("out:"+out);

// Load and instantiate compiled class.
        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{root.toURI().toURL()});
        Class<?> cls = Class.forName("test.Test", true, classLoader); // Should print "hello".
        Runnable runnable = (Runnable) cls.newInstance(); // Should print "world".
        runnable.run();
    }

    @Test
    public void test1() {


    }
}
