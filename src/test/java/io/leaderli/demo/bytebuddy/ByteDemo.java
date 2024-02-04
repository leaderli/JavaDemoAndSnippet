package io.leaderli.demo.bytebuddy;

public class ByteDemo {

    static {
        if (true) {
            throw new IllegalStateException("123");
        }
    }

    public static void test() {

        System.out.println("fuck1");
    }
}
