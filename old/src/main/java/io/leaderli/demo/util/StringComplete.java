package io.leaderli.demo.util;

import static io.leaderli.litool.core.text.StringUtils.complete;

public class StringComplete {



    public static void main(String[] args) {
        String find = "345";
        System.out.println(complete("30405", find));
        System.out.println(complete("34005", find));
        System.out.println(complete("3405", find));
        System.out.println(complete("3045", find));
        System.out.println(complete("30045", find));
        System.out.println(complete("34500", find));
        System.out.println(complete("345", find));
        System.out.println(complete("305", find));
        System.out.println(complete("4Gaqngjr6kEH8xNu3mJ1", "4"));
        System.out.println(complete("12345", "1234"));
        System.out.println(complete("12345", "123456"));

    }
}
