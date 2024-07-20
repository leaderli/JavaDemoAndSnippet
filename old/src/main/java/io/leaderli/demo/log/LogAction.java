package io.leaderli.demo.log;

import ch.qos.logback.core.joran.action.Action;
import ch.qos.logback.core.joran.spi.ActionException;
import ch.qos.logback.core.joran.spi.InterpretationContext;
import org.xml.sax.Attributes;

/**
 * @author leaderli
 * @since 2022/3/17 2:07 PM
 */
public class LogAction extends Action {
    @Override
    public void begin(InterpretationContext ic, String name, Attributes attributes) throws ActionException {
        System.out.println(ic + " name:" + name + " attributes:" + attributes);
        System.out.println(ic.getObjectStack().peek());
    }

    @Override
    public void end(InterpretationContext ic, String name) throws ActionException {
        System.out.println(ic + " name:" + name + " end");

    }
}
