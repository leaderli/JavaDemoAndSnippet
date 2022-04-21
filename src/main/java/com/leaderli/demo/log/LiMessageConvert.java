package com.leaderli.demo.log;

import ch.qos.logback.classic.pattern.MessageConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

import java.util.Arrays;

/**
 * @author leaderli
 * @since 2022/4/21
 */
public class LiMessageConvert extends MessageConverter {





    @Override
    public String convert(ILoggingEvent event) {
        String sensitive_regex = getContext().getProperty("sensitive_regex");

        return event.getMessage().replaceAll(sensitive_regex,"***");
    }
}
