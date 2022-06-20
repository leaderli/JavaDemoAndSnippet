package io.leaderli.demo;

import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * @author leaderli
 * @since 2022/2/24 9:59 AM
 */
public class TestGuava {


    @Test
    public void test() throws Throwable{

        Set s1 = new HashSet();
        s1.add("1");
        s1.add("2");

        Set s2 = new HashSet();
        s2.add("a");
        s2.add("b");

        Set s3 = new HashSet();
        s3.add("~");
        s3.add("!");

        System.out.println(Sets.cartesianProduct(s1, s2,s3));

    }
}
