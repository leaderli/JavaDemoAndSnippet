package io.leaderli.demo;

import org.junit.jupiter.api.Test;
import org.mockito.cglib.proxy.Enhancer;
import org.mockito.cglib.proxy.MethodInterceptor;
import org.mockito.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestMockito {


    @Test
    public void test() throws Throwable {
        System.out.println(Arrays.deepEquals(io.vavr.collection.List.of("1", "2", "3").toJavaArray(), io.vavr.collection.List.of("1", "2", "3").toJavaArray()));
        System.out.println(Arrays.deepEquals(io.vavr.collection.List.of("1", "2", "3").toJavaArray(), io.vavr.collection.List.of("2", "1", "3").toJavaArray()));
        //mock creation
        List mockedList = mock(List.class);
        when(mockedList.get(0)).thenReturn("hello");
        assertEquals("hello", mockedList.get(0));

        Arrays.deepEquals(new String[]{"1", "2"}, new String[]{"1", "2"});


//        when(mockedList.get(0)).thenReturn("first");
//        when(mockedList.get(1)).thenThrow(new RuntimeException());
//
//        //following prints "first"
//        System.out.println(mockedList.get(0));
//
//        //following throws runtime exception
//        System.out.println(mockedList.get(1));
//
//        //following prints "null" because get(999) was not stubbed
//        System.out.println(mockedList.get(999));
//
//        //Although it is possible to verify a stubbed invocation, usually it's just redundant
//        //If your code cares what get(0) returns, then something else breaks (often even before verify() gets executed).
//        //If your code doesn't care what get(0) returns, then it should not be stubbed.
//        verify(mockedList).get(0);
    }

    @Test
    public void test2() throws Throwable {
        List mock = Mock.mock(List.class);
        System.out.println(mock);
        Mock.when(mock.toString()).thenReturn("fuck");
        System.out.println(mock);
    }

    public static class Mock {
        private static final Map<Invocation, Object> results = new HashMap<Invocation, Object>();
        private static Invocation lastInvocation;

        @SuppressWarnings("unchecked")
        public static <T> T mock(Class<T> clazz) {

            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(clazz);
            enhancer.setCallback(new MockInterceptor());
            return (T) enhancer.create();
        }

        public static <T> When<T> when(T o) {
            return new When<>();
        }

        private static class Invocation {
            private final Object mock;
            private final Method method;
            private final Object[] arguments;
            private final MethodProxy proxy;

            public Invocation(Object mock, Method method, Object[] args, MethodProxy proxy) {
                this.mock = mock;
                this.method = method;
                this.arguments = copyArgs(args);
                this.proxy = proxy;
            }

            private Object[] copyArgs(Object[] args) {
                Object[] newArgs = new Object[args.length];
                System.arraycopy(args, 0, newArgs, 0, args.length);
                return newArgs;
            }

            @Override
            public boolean equals(Object obj) {
                if (obj == null || !obj.getClass().equals(this.getClass())) {
                    return false;
                }
                Invocation other = (Invocation) obj;
                return this.mock == other.mock && this.method.equals(other.method) && this.proxy.equals((other).proxy)
                        && Arrays.deepEquals(arguments, other.arguments);
            }

            @Override
            public int hashCode() {
                return 1;
            }
        }

        private static class MockInterceptor implements MethodInterceptor {


            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                Invocation invocation = new Invocation(obj, method, args, proxy);
                lastInvocation = invocation;
                return results.get(invocation);
            }
        }

        private static class When<T> {
            public void thenReturn(T retObj) {
                results.put(lastInvocation, retObj);
            }
        }
    }

}
