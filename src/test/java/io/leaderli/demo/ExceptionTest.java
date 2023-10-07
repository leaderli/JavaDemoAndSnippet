package io.leaderli.demo;

public class ExceptionTest {

    public static void main(String[] args) {

        System.out.println("->"+test());

    }


    private static int test() {

        try {

            int a = 1;
            System.out.println("2");
            a = 2;
            a = a/0;
            return a;
        } catch (Throwable throwable){

            System.exit(1);
        }
        finally {
            System.out.println("3");
            return 4;

        }
    }
}
