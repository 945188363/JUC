package com.njh.JUCTest.Semaphore;

import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;

public class MyThread implements Callable<Integer> {
    //底层为AQS
    //信号量为三个，每次只能处理三个线程。
    private Semaphore semaphore = new Semaphore(3);

    @Override
    public Integer call() throws Exception {
        semaphore.acquire();
        int i = (int)((Math.random()*1000)%100);  //三个线程输出
        semaphore.release();
        return i;
    }
}
