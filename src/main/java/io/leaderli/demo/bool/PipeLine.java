package io.leaderli.demo.bool;


import java.util.Arrays;
import java.util.function.Predicate;

public class PipeLine<T> {


    /**
     * 在调用end时，需要找到第一个节点进行运算
     */
    private PipeLine<T> prev;
    /**
     * 存储结果，仅在显式new PipeLine时初始化
     */
    private Bool bool;
    /**
     * 每个PipeLine绑定一个操作
     */
    private Sink<T> sink;


    private PipeLine(Bool bool) {
        this.bool = bool;
    }

    public PipeLine() {
        this.bool = new Bool();
        begin();
    }

    /**
     * 本身不做任何逻辑运算，仅锚定开始位置
     */
    private void begin() {
        this.sink = new Sink<T>(bool, Sink.Type.BEGIN) {
            @Override
            public Sink<T> accept(T test) {
                return next;
            }

            @Override
            public void valid() {
                valid(Type.TEST, Type.NOT);

            }
        };
        this.prev = null;
    }

    /**
     * 实现链表
     *
     * @param sink 当前运算逻辑
     * @param type 运算结果
     * @return 新增链表节点并返回
     */
    private PipeLine<T> add(Sink<T> sink, Sink.Type type) {
        PipeLine<T> pipeLine = new PipeLine<>(this.bool);
        pipeLine.prev = this;
        pipeLine.sink = sink;
        return pipeLine;
    }

    public PipeLine<T> test(Predicate<T> predicate) {
        assert predicate != null;
        Sink<T> sink = new Sink<T>(bool, Sink.Type.TEST) {
            @Override
            public void valid() {
                valid(Type.END, Type.AND, Type.OR);
            }
        };
        sink.predicate = predicate;
        return add(sink, Sink.Type.TEST);
    }

    /**
     * 对于or来说，如果上一个运算逻辑为true，则整个表达式都为true，所以可以直接结束
     * 否则可以直接忽略or操作前面的运算结果
     */
    public PipeLine<T> or() {
        Sink<T> sink = new Sink<T>(bool, Sink.Type.OR) {
            @Override
            public boolean cancel(Bool bool) {
                return bool.result;
            }

            @Override
            public Sink<T> accept(T test) {
                return this.next;
            }

            @Override
            public void valid() {
                valid(Type.TEST, Type.NOT);
            }
        };
        return add(sink, Sink.Type.OR);
    }

    /**
     * 返回下一个test的否定
     */
    public PipeLine<T> not() {
        Sink<T> sink = new Sink<T>(bool, Sink.Type.NOT) {

            @Override
            public Sink<T> accept(T test) {
                Sink<T> sink = this.next;
                this.next = new Sink<T>(bool, Type.TEST) {
                    @Override
                    public Sink<T> accept(T test) {
                        Sink<T> accept = sink.accept(test);
                        this.bool.result = !this.bool.result;
                        return accept;
                    }
                };
                return this.next;
            }

            @Override
            public void valid() {
                valid(Type.TEST);
            }
        };
        return add(sink, Sink.Type.NOT);
    }

    /**
     * 对于or来说，如果上一个运算逻辑为false，则整个表达式都为false，所以可以直接结束
     * 否则可以直接忽略or操作前面的运算结果
     */
    public PipeLine<T> and() {
        Sink<T> sink = new Sink<T>(bool, Sink.Type.AND) {

            @Override
            public boolean cancel(Bool bool) {
                return !bool.result;
            }

            @Override
            public Sink<T> accept(T test) {
                return this.next;
            }

            @Override
            public void valid() {
                valid(Type.TEST, Type.NOT);


            }
        };
        return add(sink, Sink.Type.OR);
    }

    /**
     * 向前查找PipeLine，同时将Sink链接起来
     *
     * @return 返回链表第一个节点，即BEGIN
     */
    public PipeLine<T> end() {
        PipeLine<T> pipeLine = this;
        Sink<T> temp = pipeLine.sink;
        //最后一个操作执行一个一定会cancel的终结节点
        temp.next = new Sink<T>(bool, Sink.Type.END) {
            @Override
            public boolean cancel(Bool result) {
                return true;
            }
        };
        PipeLine<T> pr = pipeLine.prev;
        while (pr != null) {
            pipeLine = pr;
            pr = pipeLine.prev;
            pipeLine.sink.next = temp;
            temp = pipeLine.sink;
        }
        pr = null;
        return pipeLine;
    }

    /**
     * 依次执行Sink，直到触发cancel或者所有Sink执行完成,支持多次操作
     *
     * @param test 数据
     * @return 逻辑运算结果
     */
    public boolean accept(T test) {
        return forSink(sink, test);
    }

    private static class Bool {
        boolean result = false;
    }

    private static class Sink<T> {
        /**
         * 标记操作的类型，主要用来校验表达式是否合法
         */
        public enum Type {
            BEGIN,
            TEST,
            NOT,
            OR,
            END,
            AND
        }

        protected Type type;

        public Sink(Bool bool, Type type) {
            this.bool = bool;
            this.type = type;
        }

        Bool bool;
        Predicate<T> predicate;
        /**
         * 使用链表的方式，将所有操作步骤串联起来
         */
        Sink<T> next;

        /**
         * 是否需要提前结束表达式
         */
        public boolean cancel(Bool result) {
            return false;
        }

        /**
         * @param test 断言
         * @return 返回下一个操作
         */
        public Sink<T> accept(T test) {
            this.bool.result = this.predicate.test(test);
            return next;
        }

        /**
         * 表达式是否合法，一般只需要考虑当前类型操作的下一个操作类型可以为
         */
        public void valid() {
        }

        void valid(Type... types) {
            if (next == null) {
                throw new IllegalStateException("must have end()");
            }
            if (Arrays.stream(types).noneMatch(type -> next.type == type)) {
                throw new IllegalStateException(type + " --> " + Arrays.toString(types) + "; actual is : " + next.type);
            }
        }

    }

    private boolean forSink(Sink<T> sink, T test) {
        while (sink != null) {
            sink.valid();
            if (sink.cancel(bool)) {
                break;
            }
            sink = sink.accept(test);
        }
        return bool.result;
    }

}
