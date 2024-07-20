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


    public List<String> tokens = new ArrayList<>();


    private int index;

    private String next;


    public void next() {

        if (index < tokens.size()) {

            next = tokens.get(index++);
        } else {
            next = null;
        }
    }

    public void error() {
        tokens.add(index - 1, "---->");
        tokens.add(index + 1, "<----");
        String msg = StringUtils.join(" ", tokens);
        System.out.println(msg);
        LiAssertUtil.assertNotRunWithMsg(msg);

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
        if (next != null) {
            error();
        }

    }


    @Test
    void test1() {
        Assertions.assertThrows(IllegalStateException.class, ()-> parser("a", ")"));
        Assertions.assertThrows(IllegalStateException.class, () -> parser("a", "b"));

        parser("a", "and", "b", "and", "c", "or", "d");
        parser("(", "a", "and", "b", ")", "and", "c");
        parser("a", "and", "(", "b", "or", "c", ")", "and", "(", "d", "or", "e", ")");
        parser("a", "and", "(", "b", "or", "c", ")");
        Assertions.assertThrows(IllegalStateException.class, () -> parser("(", "a", "and", "b", ")", "d"));
        Assertions.assertThrows(IllegalStateException.class, () -> parser("a", "and", "b", "and", "c", "or", "d", "e"));

    }


}
