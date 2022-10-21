package io.leaderli.demo;

import cn.hutool.core.collection.IterableIter;
import org.junit.jupiter.api.Test;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

public class BeanInfoTest {
    @Test
    void test() throws IntrospectionException {

        for (PropertyDescriptor propertyDescriptor : Introspector.getBeanInfo(IterableIter.class).getPropertyDescriptors()) {
            System.out.println(propertyDescriptor);
        }

    }
}
