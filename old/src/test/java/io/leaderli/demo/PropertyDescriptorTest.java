package io.leaderli.demo;

import io.leaderli.demo.bean.Person;
import org.junit.jupiter.api.Test;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author leaderli
 * @since 2022/10/21 2:47 PM
 */
public class PropertyDescriptorTest {

    @Test
    void test() throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        BeanInfo beanInfo = Introspector.getBeanInfo(Person.class, Object.class);
        System.out.println(beanInfo);
        for (MethodDescriptor methodDescriptor : beanInfo.getMethodDescriptors()) {
            System.out.println(methodDescriptor);
            methodDescriptor.getMethod();
        }
    }
}
