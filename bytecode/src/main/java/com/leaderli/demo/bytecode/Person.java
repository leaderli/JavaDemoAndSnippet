package com.leaderli.demo.bytecode;

/**
 * @author leaderli
 * @since 2022/6/14
 */
public class Person {

    private int aAge;

    private String aName;

    public int getAAge() {
        return aAge;
    }

    public void setAAge(int aAge) {
        this.aAge = aAge;
    }

    public String getFuck() {
        return aName;
    }

    public void setFuck(String aName) {
        this.aName = aName;
    }

    public static void test(int a ){
        System.out.println("123");
    }
    @Override
    public String toString() {
        return "Person{" +
                "aAge=" + aAge +
                ", aName=" + aName +
                '}';
    }
}
