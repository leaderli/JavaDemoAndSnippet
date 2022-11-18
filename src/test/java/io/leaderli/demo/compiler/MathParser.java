package io.leaderli.demo.compiler;

import io.leaderli.litool.core.exception.LiAssertUtil;
import io.leaderli.litool.core.meta.Lira;
import io.leaderli.litool.core.text.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author leaderli
 * @since 2022/11/18 10:08 PM
 */
public class MathParser {

    public static final String AND = "and";
    public static final String OR = "or";
    public static final String left = "(";
    public static final String right = ")";

    public static final String less = "<";
    public static final String less_equal = "<=";
    public static final String equal = "==";
    public static final String great_equal = ">=";
    public static final String great = ">";


    public List<String> tokens = new ArrayList<>();
    StringBuilder temp = new StringBuilder();

    public void pushToken() {

        if (temp.length() > 0) {
            tokens.add(temp.toString());
            temp = new StringBuilder();
        }
    }


    private int index;

    private String next;


    public void next() {

        if (index < tokens.size()) {

            next = tokens.get(index++);
        } else {
            next = null;
        }
    }

    // T { and T | or T }
    public void E() {


        T();
        while (StringUtils.equalsAny(next, "and", "or")) {
            next();
            T();
        }

        if (!(next == null || next.equals(")"))) {
            error();
        }


    }

    public void error() {
        tokens.add(index - 1, "---->");
        tokens.add(index + 1, "<----");
        LiAssertUtil.assertNotRunWithMsg(StringUtils.join(" ", tokens));

    }

    // d | ( E )
    public void T() {

        if (next == null) {
            error();
        }

        if (next.equals("(")) {
            next();
            E();
            if (!next.equals(")")) {
                error();
            }
        } else if (next.equals("and") || next.equals("or")) {
            error();
        }
        next();
    }

    public void parser(String... text) {
        index = 0;
        tokens = Lira.of(text).get();
        next();
        E();

        LiAssertUtil.assertTrue(index == tokens.size());
    }


    @Test
    void test1() {
        parser("a");
        Assertions.assertThrows(IllegalStateException.class, () -> parser("a", "b"));

        parser("a", "and", "b", "and", "c", "or", "d");
        parser("(", "a", "and", "b", ")", "and", "c");
        parser("a", "and", "(", "b", "or", "c", ")", "and", "(", "d", "or", "e", ")");
        parser("a", "and", "(", "b", "or", "c", ")");
        Assertions.assertThrows(IllegalStateException.class, () -> parser("(","a", "and", "b", ")",  "d"));
        Assertions.assertThrows(IllegalStateException.class, () -> parser("a", "and", "b", "and", "c", "or", "d", "e"));

    }


}
