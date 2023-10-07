package io.leaderli.demo;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.apache.commons.text.StringSubstitutor;
import org.junit.jupiter.api.Test;

import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

/**
 * @author leaderli
 * @since 2022/1/30
 */
public class StringTest {

    @Test
    void test1() {

        HashMap<String, Object> valueMap = new HashMap<>();
        valueMap.put("date", new Date());

        StringSubstitutor sub = new StringSubstitutor(valueMap);
        sub.setEnableSubstitutionInVariables(true);
        StringSubstitutor interpolator = StringSubstitutor.createInterpolator();
        interpolator.setEnableSubstitutionInVariables(true);
        System.out.println(sub.replace("123 ${date:yyyy-MM-dd}"));


        System.out.println(StrSubstitutor.replace("123 $${${date}}", valueMap));
    }

    @Test
    public void test() {


        System.out.printf("flow[@name='%s']%n", "fuck");
        System.out.println(MessageFormat.format("flow[@name='{0}']", "fuck"));


    }

    static Object OK(Function<Integer, Integer> function) {

        return AA.class;
    }

    static Object Err(Function<Throwable, Integer> function) {

        return AA.class;
    }

    class AA<T> {

        AA(T t) {

        }
    }

    @Test
    void test3() {

        OK(n -> n);
        Err(n -> 1);

    }
}
