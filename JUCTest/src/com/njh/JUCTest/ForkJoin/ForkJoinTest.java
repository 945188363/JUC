package com.njh.JUCTest.ForkJoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;


/**
 * @ClassName:ForkJoinTest
 * @Author:njh
 * @Description:forkJoin
 */
public class ForkJoinTest {

    public static void main(String[] args) {
        // Fork/Join线程池
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Integer> taskFuture =  pool.submit(new ForkJoinAddTask(1,1001));
        try {
            // ForkJoinTask<T> 继承了Future接口，因此get是阻塞方法
            Integer result = taskFuture.get();
            System.out.println("result = " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace(System.out);
        }
    }

}
