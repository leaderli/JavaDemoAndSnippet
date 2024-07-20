package io.leaderli.demo.log;

import ch.qos.logback.core.FileAppender;

/**
 * @author leaderli
 * @since 2022/3/16
 */
public class LiFileAppender<E> extends FileAppender<E> {

    private String sex;

    public void setSex(String sex) {
        this.sex = sex;
        System.out.println("setname " + sex);
    }

    public void addSex(String name) {
        System.out.println("add" + name);
    }

    @Override
    public void start() {
        System.out.println("1");
        super.start();
        System.out.println("2");
    }
}
