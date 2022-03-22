package com.leaderli.demo;

import ch.qos.logback.core.PropertyDefinerBase;

/**
 * @author leaderli
 * @since 2022/3/15
 */
public class LiPropertyDefiner extends PropertyDefinerBase {
    private String level;

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String getPropertyValue() {

        System.out.println("level:"+level);
        return level;
    }
}
