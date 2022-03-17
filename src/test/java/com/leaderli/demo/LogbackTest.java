package com.leaderli.demo;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author leaderli
 * @since 2022/3/17 2:10 PM
 */
public class LogbackTest {

    @Test
    public void test() throws Throwable{

        Logger logger = LoggerFactory.getLogger("HelloWorld");
        logger.debug("Hello world.");

    }
}
