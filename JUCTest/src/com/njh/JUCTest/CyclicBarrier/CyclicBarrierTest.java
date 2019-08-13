package com.njh.JUCTest.CyclicBarrier;

import com.njh.JUCTest.CountDownLatch.MyThread;

import java.util.concurrent.*;

public class CyclicBarrierTest {
    public static void main(String[] args) {
        //BlockingQueue意义：当有任务时，先进入队列等待corePoolSize处理任务。当队列满了开始创建多余的线程进行处理(多余线程+corePoolSize<maximumPoolSize)
        //ArrayBlockingQueue锁不分离，有界，要指定大小。LinkedBlockingQueue锁分离(putLock,getLock)，无界，不需要指定大小，而且maximumPoolSize失效(队列永远不会满)。
        ExecutorService pool = new ThreadPoolExecutor(3,3,100, TimeUnit.SECONDS,new ArrayBlockingQueue<>(10));

        try {
            //Future接口通过ThreadPoolExecutor的Submit()返回异步结果---------ThreadPoolExecutor的Execute()返回空
            for (int i = 0; i < 5 ; i++) {
                com.njh.JUCTest.CountDownLatch.MyThread mt = new com.njh.JUCTest.CountDownLatch.MyThread();
                Future<Integer> future = pool.submit(mt);
                try{
                    System.out.println(future.get());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
