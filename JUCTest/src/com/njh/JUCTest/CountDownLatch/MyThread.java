package com.njh.JUCTest.CountDownLatch;

import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;

public class MyThread implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        int i = (int)((Math.random()*1000)%100);
        return i;
    }
}
