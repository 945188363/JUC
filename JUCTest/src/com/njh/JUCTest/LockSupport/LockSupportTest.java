package com.njh.JUCTest.LockSupport;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.LockSupport;

/**
 * @ClassName:LockSupportTest
 * @Author:njh
 * @Description:TODO
 */
public class LockSupportTest {
    private final static int THREAD_SIZE = 3;
    public static void main(String[] args) {
        // 三个线程顺序打印1-100数字
        var threadList = new ArrayList<Thread>(THREAD_SIZE);
        var cnt = new AtomicInteger(0);
        for (int i = 0; i < THREAD_SIZE; i++) {
            threadList.add(new Thread(()->{
                while (true) {
                    LockSupport.park();
                    if (cnt.get() == 100) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                    var currentIndex = cnt.incrementAndGet();
                    System.out.println(Thread.currentThread().getName() + " : " + currentIndex);
                    LockSupport.unpark(threadList.get(currentIndex % 3));
                }
            }));
        }
        threadList.forEach(Thread::start);
        LockSupport.unpark(threadList.get(0));
    }
}
