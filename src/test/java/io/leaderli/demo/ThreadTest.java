package io.leaderli.demo;

import io.leaderli.demo.util.SomeUtils;

public class ThreadTest {
    public static void main(String[] args) throws InterruptedException {
        Thread T1 = new Thread(() -> {
            System.out.println("T1");
            SomeUtils.sleep(3000);
        });
        Thread T2 = new Thread(() -> {
            System.out.println("T2");
            SomeUtils.sleep(2000);
        });
        Thread T3 = new Thread(() -> {
            System.out.println("T3");
            SomeUtils.sleep(1000);
        });

        T1.start();
        T1.join();
        T2.start();
        T2.join();
        T3.start();
        T3.join();
    }


    static class NumberPrint implements Runnable {
        private int number;
        public byte res[];
        public static int count = 5;

        public NumberPrint(int number, byte a[]) {
            this.number = number;
            res = a;
        }

        public void run() {
            synchronized (res) {
                while (count-- > 0) {
                    try {

                        res.notify();//唤醒等待res资源的线程，把锁交给线程（该同步锁执行完毕自动释放锁）
                        System.out.println(" " + number);

                        res.wait();//释放CPU控制权，释放res的锁，本线程阻塞，等待被唤醒。
                        System.out.println("------线程" + Thread.currentThread().getName() + "获得锁，wait()后的代码继续运行：" + number);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }//end of while
                return;
            }//synchronized

        }
    }

    public static class WaitNotify {
        public static void main(String args[]) {
            final byte a[] = {0};//以该对象为共享资源
            new Thread(new NumberPrint((1), a), "1").start();
            new Thread(new NumberPrint((2), a), "2").start();
        }
    }
}
