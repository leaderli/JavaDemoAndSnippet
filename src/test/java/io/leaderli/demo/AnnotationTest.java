package io.leaderli.demo;


import org.junit.jupiter.api.Test;
import sun.reflect.annotation.AnnotationSupport;

import java.lang.annotation.*;
import java.util.Arrays;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.TYPE})
@Repeatable(NotNulls.class)
@interface NotNull {

    String value();


}

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.TYPE})
@interface NotNulls {
    NotNull[] value();
}

@NotNull("123")
@NotNull("456")
public class AnnotationTest {

    @Test
    void test() {




    }

}
