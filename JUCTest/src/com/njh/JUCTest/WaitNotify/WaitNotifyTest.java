package com.njh.JUCTest.WaitNotify;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName:WaitNotifyTest
 * @Author:njh
 * @Description:TODO
 */
public class WaitNotifyTest {
    private final static int THREAD_SIZE = 3;
    public static void main(String[] args) throws InterruptedException {
        // 两个线程顺序打印1-100数字
        Object lock = new Object();
        AtomicInteger a = new AtomicInteger(0);
        Thread ta = new Thread(()->{
            synchronized (lock) {
                while (true) {
                    if (a.get() == 100) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                    System.out.println(Thread.currentThread().getName() + " : "  + a.incrementAndGet());
                    try {
                        // wait 会阻塞当前线程，释放锁，并将当前线程放入到object `lock`(即monitor)的wait Set。
                        // wait notify方法放在Object里是为了方便调用，因为每个object对象又有MarkWord来存放锁。
                        // 正常来讲，放入Thread当中也可以： Thread.wait(Object o)，传入的对象为当前线程持有的锁对象。
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        // 从wait Set当中选择一个thread加入到entry Set中，去抢锁。 FIFO
                        lock.notify();
                        // 取出wait Set当中所有的thread加入到entry Set中抢锁。 FILO
                        // lock.notifyAll();
                    }
                }
            }
        });

        Thread tb = new Thread(()->{
            synchronized (lock) {
                while (true) {
                    if (a.get() == 100) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                    System.out.println(Thread.currentThread().getName() + " : " + a.incrementAndGet());
                    lock.notify();
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        ta.start();
        tb.start();
        ta.join();
        tb.join();
    }
}
