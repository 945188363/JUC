package com.njh.JUCTest.Executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CatchedThreadPoolTest {
    public static void main(String[] args) {
        //缓存线程池
        //使用场景：任务量大但耗时少的任务
        /**
         * 缓存队列参数描述(默认参数，已经配置好的)
         * @param corePoolSize = 0   线程都可以空闲
         * @param maximumPoolSize = Integer.MAX_VALUE  随时创建线程
         * @param keepAliveTime = 60L
         * @param TimeUnit = TimeUnit.SECONDS
         * @param BlockingQueue = new SynchronousQueue<Runnable>()  任务立即执行，corePoolSize失效
         * @param ThreadFactory  创建线程的工厂
         */
        ExecutorService ctp = Executors.newCachedThreadPool();

        //测试
        /**
         * lambda表达式相比较匿名内部类
         * lambda：lambda使用Invokedynamic字节指令，性能高(类似CgLib代理，在汇编层面改变)
         * 匿名内部类：在编译时会创建一个新的类，性能低
         */
        for (int i = 0; i < 100; i++) {
            ctp.execute(()->{
                System.out.println("大家好，我是线程。");
            });
        }
    }
}
