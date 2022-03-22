package com.leaderli.demo.spring;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SpringTest {

    private String name ="spring";
    public String log() {
        return "log";
    }


    @Test
    public void test() throws InvocationTargetException, IllegalAccessException {

        Method log = ReflectionUtils.findMethod(SpringTest.class, "log");
        Field name = ReflectionUtils.findField(SpringTest.class, "name");
        System.out.println(name.get(this));

        assert log != null;
        Assertions.assertEquals("log", log.getName());

        ReflectionUtils.makeAccessible(log);
        Object invoke = log.invoke(this);
        Assertions.assertEquals("log", invoke);

    }
}
