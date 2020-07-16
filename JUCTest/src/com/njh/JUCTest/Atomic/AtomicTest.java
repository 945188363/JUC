package com.njh.JUCTest.Atomic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName:SynchronizeTest
 * @Author:njh
 * @Description:a test for threadLocal
 */
public class AtomicTest {
    public static void main(String[] args) {
        // 创建线程池
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10,10,60*10, TimeUnit.SECONDS,new LinkedBlockingDeque<>(), (r,e)->{});

        // 原子类
        AtomicInteger i = new AtomicInteger(1);

        for (int j = 0; j < 10; j++) {
            executor.execute(()->{
                i.getAndIncrement();
                System.out.println(i.get());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } );
        }
        // 不同步
        List sl = new ArrayList<Integer>(1);
        sl.add(1);
        for (int j = 0; j < 10; j++) {
            executor.execute(()->{
                if (sl.stream().findFirst().isPresent()){
                    System.out.println(sl.stream().findFirst().get());
                    int ss = (int) sl.stream().findFirst().get();
                    sl.clear();
                    sl.add(++ss);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } );
        }
    }
}
