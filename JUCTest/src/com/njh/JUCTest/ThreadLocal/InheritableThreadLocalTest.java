package com.njh.JUCTest.ThreadLocal;

import java.util.HashMap;

/**
 * @ClassName:InheriteThreadLocal
 * @Author:njh
 * @Description:TODO
 */
public class InheritableThreadLocalTest {
    // 多线程传值原理：
    // Thread 构造函数：
    /*
     * Thread parent = currentThread();
     * if (inheritThreadLocals && parent.inheritableThreadLocals != null)
     *     this.inheritableThreadLocals = ThreadLocal.createInheritedMap(parent.inheritableThreadLocals);
     **/
    private static InheritableThreadLocal<HashMap<String,String>> city = new InheritableThreadLocal<>();

    public static void initCity(){
        HashMap<String,String> cityMap = new HashMap<>();
        // 在main线程中添加初始数据
        cityMap.put("jiangsu","nanjing");
        cityMap.put("zhejiang","hangzhou");
        cityMap.put("shandong","jinan");
        cityMap.put("hubei","wuhan");
        city.set(cityMap);
    }

    public static void main(String[] args) throws InterruptedException {
        initCity();
        // 多线程去取inheritable thread local中的数据

        // t1线程获取不到t2和main线程数据
        Thread t1 = new Thread(()->{
            System.out.println("t1 Thread origin city : " + city.get());
            var origin = city.get();
            origin.put("t1", "t1");
            System.out.println("t1 Thread origin city : " + city.get());
        });
        // t2获取不到main线程和t1线程的数据
        Thread t2 = new Thread(()->{
            System.out.println("t2 Thread origin city : " + city.get());
            var origin = city.get();
            origin.put("t2", "t2");
            System.out.println("t2 Thread origin city : " + city.get());
        });

        t1.start();t2.start();
        // 等待t1、t2线程执行完再执行下面的命令
        t1.join();t2.join();
        // main获取不到t1、t2线程的数据
        System.out.println("main Thread city : " + city.get());
    }
}
