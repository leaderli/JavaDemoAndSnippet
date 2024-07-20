package io.leaderli.demo;

import io.leaderli.litool.core.text.StringUtils;
import org.junit.jupiter.api.Test;

@SuppressWarnings("all")
public class ExceptionTest {

    @Test
    void test1() {

        try {
            throw new RuntimeException();
        } catch (Throwable e) {
            e.printStackTrace();
            System.out.println(StringUtils.localMessageStartWith(e, ""));
        }
    }

    public static void main(String[] args) {

        System.out.println("->" + test());

    }


    private static int test() {

        try {

            int a = 1;
            System.out.println("2");
            a = 2;
            a = a / 0;
            return a;
        } catch (Throwable throwable) {

            System.exit(1);
        } finally {
            System.out.println("3");
            return 4;

        }
    }
}
