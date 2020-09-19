package com.njh.JUCTest.ForkJoin;

import java.util.concurrent.RecursiveTask;

/**
 * @ClassName:ForkJoinTask
 * @Author:njh
 * @Description:ForkJoinAddTask
 */
public class ForkJoinAddTask extends RecursiveTask<Integer> {

    // 根据计算和之间不超过两百来作为条件Fork子任务
    private static final Integer MAX = 200;

    // 子任务开始计算的值
    private Integer startValue;

    // 子任务结束计算的值
    private Integer endValue;

    public ForkJoinAddTask(Integer startValue , Integer endValue) {
        this.startValue = startValue;
        this.endValue = endValue;
    }

    @Override
    protected Integer compute() {
        // 如果条件成立，说明这个任务所需要计算的数值分为足够小了
        // 可以正式进行累加计算了
        if(endValue - startValue < MAX) {
            System.out.println("开始计算的部分：startValue = " + startValue + ";endValue = " + endValue);
            Integer totalValue = 0;
            for(int index = this.startValue ; index <= this.endValue  ; index++) {
                totalValue += index;
            }
            return totalValue;
        }
        // 否则再进行任务拆分，拆分成两个任务
        else {
            ForkJoinAddTask subTask1 = new ForkJoinAddTask(startValue, (startValue + endValue) / 2);
            subTask1.fork();
            ForkJoinAddTask subTask2 = new ForkJoinAddTask((startValue + endValue) / 2 + 1 , endValue);
            subTask2.fork();
            return subTask1.join() + subTask2.join();
        }
    }
}
