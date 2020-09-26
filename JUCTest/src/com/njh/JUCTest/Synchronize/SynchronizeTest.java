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
    private static int staticVar = 0;
    public static void main(String[] args) {
        // 创建线程池
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10,10,60*10, TimeUnit.SECONDS,new LinkedBlockingDeque<>(), (r,e)->{});

        // synchronized同步
        List l = new ArrayList<Integer>(1);
        l.add(1);

        // 以下lock对象作为锁可以同步，原因是每个线程的lock对象实例一致
        final String lock = new String("lock");

        for (int j = 0; j < 10; j++) {
            executor.execute(()->{
                // 锁住当前list变量相关
//                synchronized (l){
//                    if (l.stream().findFirst().isPresent()){
//                        System.out.println(l.stream().findFirst().get());
//                        int ss = (int) l.stream().findFirst().get();
//                        l.clear();
//                        l.add(++ss);
//                    }
//                }
                // 类锁
                // 类锁锁住的是当前类，与此类的静态方法互斥
                synchronized (SynchronizeTest.class){
                    // do something
                    if (l.stream().findFirst().isPresent()){
                        System.out.println(l.stream().findFirst().get());
                        int ss = (int) l.stream().findFirst().get();
                        l.clear();
                        l.add(++ss);
                    }
//                    addSomething();
                }

//                System.out.println(staticVar);

                // 对象锁
                // 锁住的是当前对象实例，在同一对象实例下互斥，否则不互斥

                // 以下不能同步，原因是每个线程的lock对象实例不一致
                // final String lock = new String("lock");
//                synchronized (lock){
//                    // do something
//                    if (l.stream().findFirst().isPresent()){
//                        System.out.println(l.stream().findFirst().get());
//                        int ss = (int) l.stream().findFirst().get();
//                        l.clear();
//                        l.add(++ss);
//                    }
//                }
            } );
        }

//        // 不同步
//        List sl = new ArrayList<Integer>(1);
//        sl.add(1);
//        for (int j = 0; j < 10; j++) {
//            executor.execute(()->{
//                if (sl.stream().findFirst().isPresent()){
//                    System.out.println(sl.stream().findFirst().get());
//                    int ss = (int) sl.stream().findFirst().get();
//                    sl.clear();
//                    sl.add(++ss);
//                }
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            } );
//        }
    }

    public synchronized static void addSomething(){
        staticVar++;
    }

    public  static void addSomething1(){
        staticVar++;
    }
}
