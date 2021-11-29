package com.leaderli.demo.demo;


import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

public class PropertiesTest {
    @Test
    public void test1() throws IOException {
        File file = new File(".");
        System.out.println("file = " + file);
        System.out.println("file = " + file.getAbsolutePath());
        System.out.println("file.listFiles() = " + Arrays.toString(file.listFiles()));
        System.out.println(PropertiesTest.class.getResource(".").getFile());
        InputStream inputStream = PropertiesTest.class.getResourceAsStream("test.properties");
        System.out.println("inputStream = " + inputStream);
        Properties properties  = new Properties();
        properties.load(inputStream);
        System.out.println(properties);


    }
}
