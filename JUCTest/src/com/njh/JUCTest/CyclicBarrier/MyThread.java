package com.njh.JUCTest.CyclicBarrier;

import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class MyThread implements Callable<Integer> {
    //子任务设置为5
    CyclicBarrier cb = new CyclicBarrier(5);

    @Override
    public Integer call() throws Exception {
        cb.await();//等待所有子线程
        int i = (int)((Math.random()*1000)%100);  //三个线程输出
        return i;
    }
}
