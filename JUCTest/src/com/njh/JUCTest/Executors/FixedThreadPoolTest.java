package com.njh.JUCTest.Executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FixedThreadPoolTest {
    public static void main(String[] args) {
        //混合线程池
        //使用场景：任务量比较固定但耗时长的任务
        /**
         * 混合队列参数描述(默认参数，已经配置好的)
         * @param nThreads 最大和核心线程数量
         * @param corePoolSize = nThreads   创建实例时输入
         * @param maximumPoolSize = nThreads  创建实例时输入
         * @param keepAliveTime = 0L  无效 所有线程都是核心线程，不回收线程
         * @param TimeUnit = TimeUnit.SECONDS
         * @param BlockingQueue = new LinkedBlockingQueue<Runnable>()
         */
        ExecutorService ftp = Executors.newFixedThreadPool(5);

        //测试
        /**
         * lambda表达式相比较匿名内部类
         * lambda：lambda使用Invokedynamic字节指令，性能高(类似CgLib代理，在汇编层面改变)
         * 匿名内部类：在编译时会创建一个新的类，性能低
         */
        for (int i = 0; i < 100; i++) {
            ftp.execute(()->{
                System.out.println("大家好，我是线程。");
            });
        }
    }
}
