package io.leaderli.demo;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author leaderli
 * @since 2022/3/14
 */
public class LogbackTest {
   static Logger logger = LoggerFactory.getLogger("STDOUT");
    static Logger sensitive = LoggerFactory.getLogger("sensitive");


    @Test
    public void test() {

        logger.error("error");
        logger.info("info");
        logger.debug("debug");
        sensitive.error("error");
        sensitive.info("info");
        sensitive.debug("debug");
        logger.error("debug1234");
        sensitive.error("debug1234");
        sensitive.error("debug12");
    }
}
