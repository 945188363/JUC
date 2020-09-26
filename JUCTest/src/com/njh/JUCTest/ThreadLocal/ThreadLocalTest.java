package com.njh.JUCTest.ThreadLocal;


import java.util.HashMap;


/**
 * @ClassName:ThreadLocalTest
 * @Author:njh
 * @Description:a test for threadLocal
 */
public class ThreadLocalTest {
    private static ThreadLocal<HashMap<String,String>> city = new ThreadLocal<>();

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
        // 多线程去取thread local中的数据

        // t1线程获取不到t2和main线程数据
        Thread t1 = new Thread(()->{
            synchronized (city){
                HashMap<String,String> t1Map = new HashMap<>();
                t1Map.put("t1", "t1");
                city.set(t1Map);
                // 立即让出cpu时间片，立即 使当前线程从执行态进入就绪态，cpu会从就绪态的线程从随机选择一个执行
                // 即 当前线程会重新与其他就绪态线程进行竞争,但不让出 锁
                // Thread.yield();
                try {
                    // 立即让出cpu时间片，但是不让出锁，sleep完时间后当前线程进入就绪态
                    // Thread.sleep(1000);

                    // 立即让出cpu时间片，释放锁（必须在同步代码块中执行），当前线程进入阻塞态，直到被notify进入就绪态
                    // <object>.wait()中object最好是sync锁住的对象
                    city.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(city.get());
            }
        });
        // t2获取不到main线程和t1线程的数据
        Thread t2 = new Thread(()->{
            synchronized (city) {
                HashMap<String, String> t2Map = new HashMap<>();
                t2Map.put("t2", "t2");
                city.set(t2Map);
                System.out.println(city.get());

                // <object>.notify 和 <object>.notifyAll 必须在同步代码块中执行
                // 唤醒所有因调用<object>.wait而进入阻塞态的线程
                city.notifyAll();
                // 唤醒一个因调用<object>.wait而进入阻塞态的线程
                city.notify();
            }
        });

        // 巧用Thread.interrupt 优雅的终止线程
        Thread t3 = new Thread(()->{
            int i = 0;
            while (!Thread.currentThread().isInterrupted()){
                System.out.println(++i);
            }
            System.out.println("Thread: t3 is stopped." + Thread.currentThread().getName());
        },"t3");

        t1.start();t2.start();t3.start();
        // 等待t1、t2线程执行完再执行下面的命令
        t1.join();t2.join();
        // 等待1s 中断t3
        Thread.sleep(10);
        t3.interrupt();
        t3.join();
        // main获取不到t1、t2线程的数据
        System.out.println(city.get());
    }
}
