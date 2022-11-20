package io.leaderli.demo.compiler;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MathParser2 {

    private int index;
    private String next;

    private final List<String> words;

    public MathParser2(String... words) {
        this.words = new ArrayList<>(Arrays.asList(words));
    }

    public void next() {

        if (index < words.size()) {
            next = words.get(index++);
        } else {
            next = null;
        }
    }

    public void check() {

        next();
        E();

    }

    // E -> T{and T|or T}
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

    // T -> F|(E)
    public void T() {
        if (next == null) {
            error();
        } else if (next.equals("(")) {
            next();
            E();
            if (!StringUtils.equals(next, ")")) {
                error();
            }
        } else if (StringUtils.equalsAny(next, "and", "or", ">", ">=", "==", "<", "<=")) {

            error();
        }
        F();
    }

    // F -> d|d>d|d>=d|d==d|d<=d|d<d|(F)
    public void F() {
        if (next.equals("(")) {
            next();
            F();
            if (!StringUtils.equals(next, ")")) {
                error();
            }
        } else if (StringUtils.equalsAny(next, null, "and", "or", ">", ">=", "==", "<", "<=")) {
            error();
        }
        next();
        if (StringUtils.equalsAny(next, ">", ">=", "==", "<", "<=")) {
            next();

            if (StringUtils.equalsAny(next, "and", "or", ">", ">=", "==", "<", "<=")) {
                error();
            }
            next();
        }
        if (!StringUtils.equalsAny(next, null, "and", "or", ")")) {
            error();
        }
    }

    public void error() {
        words.add(index - 1, "---->");
        words.add(index + 1, "<----");
        String msg = StringUtils.join(" ", words);
        throw new RuntimeException(msg);
    }

    public static void test(String expression) {
        MathParser2 mathParser2 = new MathParser2(expression.split("\\s+"));
        List<String> expressions = mathParser2.words;
        try {

            mathParser2.check();
        } catch (Throwable throwable) {

//            throwable.printStackTrace();
            System.out.println(expressions + " : error  " + throwable.getMessage());
            return;
        }
        System.out.println(expressions + " : success");

    }

    public static void main(String[] args) {

        test("a and b");
        test("a and b  or ( c > d )");
        test("a and b  or ( c > ( d ) )");
        test("a and b  or ( ( c > d ) and ( e == f ) )");
        test("a and b  or ( ( c > d ) and ( ( e == f ) and ( g ) or h or ( i < j ) ) )");
        test("a and b  or ( ( c > d ) and ( ( e == f ) and ( g ) or h or ( i < j ) )");
    }
}
