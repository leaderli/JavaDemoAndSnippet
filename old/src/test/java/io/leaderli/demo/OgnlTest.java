package io.leaderli.demo;

import io.leaderli.litool.core.collection.LiMapUtil;
import ognl.Ognl;
import ognl.OgnlException;
import org.junit.jupiter.api.Test;

public class OgnlTest {
    @Test
    void test() throws OgnlException {
        System.out.println(Ognl.getValue("name", null, LiMapUtil.newHashMap("name", "123")));
        Object name = Ognl.parseExpression("name");
        System.out.println(Ognl.getValue(name, null, LiMapUtil.newHashMap("name", "123")));

    }
}
