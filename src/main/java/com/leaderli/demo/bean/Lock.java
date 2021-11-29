package com.leaderli.demo.bean;

import java.util.Arrays;

public class Lock {
    private String use;
    private int[] fuck;

    public String getUse() {
        return use;
    }

    public void setUse(String use) {
        this.use = use;
    }

    public int[] getFuck() {
        return fuck;
    }

    public void setFuck(int[] fuck) {
        this.fuck = fuck;
    }

    @Override
    public String toString() {
        return "Lock{" +
                "use='" + use + '\'' +
                ", fuck=" + Arrays.toString(fuck) +
                '}';
    }
}
