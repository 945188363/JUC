package com.njh.JUCTest.Executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolTest {
    public static void main(String[] args) {
        //计划线程池
        //执行定时任务和具体固定周期的重复任务
        /**
         * 计划队列参数描述(默认参数，已经配置好的)
         * @param corePoolSize   创建实例时输入
         * @param maximumPoolSize = Integer.MAX_VALUE  创建实例时输入
         * @param keepAliveTime = 0L  无效 所有线程都是核心线程，不回收线程
         * @param TimeUnit = NANOSECONDS
         * @param BlockingQueue = new DelayedWorkQueue() 延迟队列
         */
        ExecutorService stp = Executors.newScheduledThreadPool(5);

        //测试
        /**
         * lambda表达式相比较匿名内部类
         * lambda：lambda使用Invokedynamic字节指令，性能高(类似CgLib代理，在汇编层面改变)
         * 匿名内部类：在编译时会创建一个新的类，性能低
         */
        /**
         * schedule()方法参数描述
         * @param Runnable 线程
         * @param delay 延迟时间
         * @param TimeUnit 时间单位
         */
        for (int i = 0; i < 100; i++) {
            ((ScheduledExecutorService) stp).schedule(()->{
                System.out.println("大家好，我是线程。延期了1S执行");
            },1L, TimeUnit.SECONDS);
        }

        /**
         * scheduleAtFixedRate()方法参数描述
         * @param Runnable 线程
         * @param initialDelay 延迟时间
         * @param period 周期时间
         * @param TimeUnit 时间单位
         */
        for (int i = 0; i < 100; i++) {
            ((ScheduledExecutorService) stp).scheduleAtFixedRate(()->{
                System.out.println("大家好，我是线程。延期了1S执行,每3S执行一次");
            },1L, 3,TimeUnit.SECONDS);
        }
    }
}
