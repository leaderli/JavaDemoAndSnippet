package com.leaderli.demo.demo;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Objects;

public class TestClassPath {

    @Test
    public void test() throws IOException, URISyntaxException {


        Enumeration<URL> resources = ClassLoader.getSystemResources("");

//        while (resources.hasMoreElements()){
//
//            scan(new File(resources.nextElement().toURI()));
//        }
      resources = TestClassPath.class.getClassLoader().getResources("META-INF");

        while (resources.hasMoreElements()){
            System.out.println(resources.nextElement());

        }

        System.out.println("-------------------------------------");
      resources = ClassLoader.getSystemResources("META-INF");
        while (resources.hasMoreElements()){
            System.out.println(resources.nextElement());

        }

    }

    private void scan(File file) {
        if(file==null||!file.exists()){
           return;
        }

        System.out.println(file);

        if(file.isDirectory()){
            for (File f: Objects.requireNonNull(file.listFiles())) {
                scan(f);
            }
        }
    }

}
