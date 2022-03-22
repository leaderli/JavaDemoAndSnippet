package com.leaderli.demo;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author leaderli
 * @since 2022/3/14
 */
public class LogbackTest {
   static Logger logger = LoggerFactory.getLogger("STDOUT");


    @Test
    public void test() {

        logger.error("error");
        logger.info("info");
        logger.debug("debug");
    }
}
