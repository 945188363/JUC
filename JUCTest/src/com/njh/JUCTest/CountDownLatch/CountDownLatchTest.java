package com.njh.JUCTest.CountDownLatch;

import com.njh.JUCTest.CountDownLatch.MyThread;

import java.util.concurrent.*;

public class CountDownLatchTest {
    public static void main(String[] args) {
        //线程并发计数器设置为5
        CountDownLatch cdl = new CountDownLatch(5);
        CountDownLatch cdl2 = new CountDownLatch(5);
        //BlockingQueue意义：当有任务时，先进入队列等待corePoolSize处理任务。当队列满了开始创建多余的线程进行处理(多余线程+corePoolSize<maximumPoolSize)
        //ArrayBlockingQueue锁不分离，有界，要指定大小。LinkedBlockingQueue锁分离(putLock,getLock)，无界，不需要指定大小，而且maximumPoolSize失效(队列永远不会满)。
        ExecutorService pool = new ThreadPoolExecutor(3,3,100, TimeUnit.SECONDS,new ArrayBlockingQueue<>(10));

        try {
            //Future接口通过ThreadPoolExecutor的Submit()返回异步结果---------ThreadPoolExecutor的Execute()返回空
            for (int i = 0; i < 5 ; i++) {
                MyThread mt = new MyThread();
                cdl.countDown();//计数器减一
                Future<Integer> future = pool.submit(mt);
                try{
                    System.out.println(future.get());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            cdl.await();//并发执行
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            //FutureTask类通过实现Runnable和Future接口可以进入线程池执行并返回异步结果
            for (int i = 0; i < 5 ; i++) {
                MyThread mt = new MyThread();
                cdl2.countDown();//计数器减一
                FutureTask<Integer> futureTask = new FutureTask<Integer>(mt);
                pool.submit(futureTask);
                try{
                    System.out.println(futureTask.get());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            cdl2.await();//并发执行
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
