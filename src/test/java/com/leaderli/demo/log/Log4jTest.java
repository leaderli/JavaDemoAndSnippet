package com.leaderli.demo.log;

import org.apache.log4j.*;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.HierarchyEventListener;
import org.junit.Test;

import java.io.IOException;
import java.util.Properties;

public class Log4jTest {

    static {
            Logger.getRootLogger().getLoggerRepository().addHierarchyEventListener(new HierarchyEventListener() {
        @Override
        public void addAppenderEvent(Category cat, Appender appender) {
            LogLog.debug("-->add " + cat.getName() + "  " + appender);
        }
        @Override
        public void removeAppenderEvent(Category cat, Appender appender) {
            //log4j配置被移除前的回调，此时配置还是生效的，所以这里重新加载是无效的，回调后就
            //被重置了，所以需要在外面去重新加载，这里仅打一个标记

            LogLog.debug("-->remove " + cat.getName() + "  " + appender);
        }
    });

    }
    private Logger COM= Logger.getLogger("com");
    private Logger COM1= Logger.getLogger("com.l1");
    private Logger COM2= Logger.getLogger("com.l2");
    @Test
    public void test(){
        COM.debug("hello");
        COM1.debug("hello1");
        COM2.debug("hello2");

        System.out.println("------------------------------------------------");
        Properties properties = new Properties();
        try {
            properties.load(ClassLoader.getSystemResourceAsStream("log4j_2.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        PropertyConfigurator.configure(properties);

        COM.debug("hello");
        COM1.debug("hello1");
        COM2.debug("hello2");

        System.out.println("------------------------------------------------");
        properties = new Properties();
        try {
            properties.load(ClassLoader.getSystemResourceAsStream("log4j.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
//        BasicConfigurator.resetConfiguration();
        PropertyConfigurator.configure(properties);
        COM.debug("hello");
        COM1.debug("hello1");
        COM2.debug("hello2");

        System.out.println("------------------------------------------------");
    }
}
