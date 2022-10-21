package io.leaderli.demo;

import cn.hutool.core.util.RandomUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.apache.commons.text.StringSubstitutor;
import org.junit.jupiter.api.Test;

import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * @author leaderli
 * @since 2022/1/30
 */
public class StringTest {

    @Test
    void test1() {

        HashMap<String, Object> valueMap = new HashMap<>();
        valueMap.put("date",new Date());

        StringSubstitutor sub = new StringSubstitutor(valueMap);
        sub.setEnableSubstitutionInVariables(true);
        StringSubstitutor interpolator = StringSubstitutor.createInterpolator();
        interpolator.setEnableSubstitutionInVariables(true);
        System.out.println(sub.replace("123 ${date:yyyy-MM-dd}"));


    }
    @Test
    public void test() {


        System.out.printf("flow[@name='%s']%n", "fuck");
        System.out.println(MessageFormat.format("flow[@name='{0}']", "fuck"));


    }
}
