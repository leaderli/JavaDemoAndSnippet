package io.leaderli.demo;

import io.leaderli.demo.bean.Person;
import org.junit.jupiter.api.Test;

import java.beans.*;
import java.lang.reflect.Method;

public class BeanInfoTest {
    @Test
    void test() throws IntrospectionException {

        BeanInfo beanInfo = Introspector.getBeanInfo(Person.class);
        for (PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors()) {

            Method readMethod = propertyDescriptor.getReadMethod();
            Method writeMethod = propertyDescriptor.getWriteMethod();
            Class<?> propertyType = propertyDescriptor.getPropertyType();
            String name = propertyDescriptor.getName();

        }

        for (MethodDescriptor methodDescriptor : beanInfo.getMethodDescriptors()) {

            methodDescriptor.getMethod();
        }


    }
}
