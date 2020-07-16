package com.njh.JUCTest.Synchronize;

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
public class SynchronizeTest {
    public static void main(String[] args) {
        // 创建线程池
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10,10,60*10, TimeUnit.SECONDS,new LinkedBlockingDeque<>(), (r,e)->{});

        // synchronized同步
        List l = new ArrayList<Integer>(1);
        l.add(1);
        for (int j = 0; j < 10; j++) {
            executor.execute(()->{
                // 锁住当前list变量相关
                synchronized (l){
                    if (l.stream().findFirst().isPresent()){
                        System.out.println(l.stream().findFirst().get());
                        int ss = (int) l.stream().findFirst().get();
                        l.clear();
                        l.add(++ss);
                    }
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
