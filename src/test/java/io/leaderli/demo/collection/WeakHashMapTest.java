package io.leaderli.demo.collection;

import io.leaderli.litool.core.util.ThreadUtil;
import org.junit.jupiter.api.Test;

public class WeakHashMapTest {


    private class Strong {

        @Override
        protected void finalize() throws Throwable {
            System.out.println("Strong GC");
        }
    }

    @Test
    void test() {



        Strong strong = new Strong();

        System.out.println("1");
        strong = null;
        ThreadUtil.sleep(10);
        System.out.println("2");
        System.gc();
        ThreadUtil.sleep(10);
        System.out.println("3");


    }
}
