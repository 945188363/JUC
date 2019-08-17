package com.njh.JUCTest.Executors;

import java.util.concurrent.*;

public class ThreadPoolExecutorTest {
    public static void main(String[] args) {
        //自定义的线程池
        //使用场景：自适应
        /**
         * 参数解释
         * @param corePoolSize 核心线程数量，底层一直出于死循环，从而导致线程不死掉
         * @param maximumPoolSize 最大线程数，当阻塞队列满的时候会在小于这个参数范围内创建新的线程来执行任务
         * @param keepAliveTime 空闲线程存活时间
         * @param TimeUnit 存活时间的单位，一般以秒记
         * @param BlockingQueue 阻塞队列，底层为可重入锁实现，有三种，分别为Array~(有界须指定大小，锁不分离的整个入列和出列用同一把锁，默认非公平)，Linked~(无界可指定大小若不指定大小则maximumPoolSize参数失效，锁分离的有takeLock和putLock，类似读写锁)，
         * @param ThreadFactory  创建线程的工厂
         * @param RejectedExecutionHandler 已达到maximumPoolSize值且队列数已满的任务拒绝策略：AbortPolicy(抛出异常)、CallerRunsPolicy(减慢提交任务速度)、DiscardPolicy(丢弃新提交任务)、DiscardOldestPolicy(队列头任务先被舍弃，重新执行任务)
         */
        ThreadPoolExecutor tpe = new ThreadPoolExecutor(5, 5, 60,
                TimeUnit.SECONDS, new LinkedBlockingQueue<>(10), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                //do somethings
                return null;
            }
        }, new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                //do somethings
            }
        });

        //测试
        /**
         * lambda表达式相比较匿名内部类
         * lambda：lambda使用Invokedynamic字节指令，性能高(类似CgLib代理，在汇编层面改变)
         * 匿名内部类：在编译时会创建一个新的类，性能低
         */
        for (int i = 0; i < 100; i++) {
            tpe.execute(()->{
                System.out.println("大家好，我是线程。");
            });
        }
    }
}
