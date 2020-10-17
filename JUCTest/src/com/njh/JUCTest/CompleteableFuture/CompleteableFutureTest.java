package com.njh.JUCTest.CompleteableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @ClassName:CompleteableFuture
 * @Author:njh
 * @Description:TODO
 */
public class CompleteableFutureTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 带有Async的方法，是使用fork/join中的work线程异步执行
        // 不带则是在当前线程执行

        // run    ()  -> void                input: null, return: null               runner
        // supply ()  -> T                   input: null, return: T                  producer
        // apply  (V) -> T                   input: V,    return: T                  producer
        // accept (V) -> void                input: V,    return: null               consumer

        // CompletableFuture提供两个静态方法作为入口： run()/runAsync()、supply()/supplyAsync()
        // 这两种方法不需要入参，所以可以作为入口
        CompletableFuture<String> t1 = CompletableFuture
                .supplyAsync(()->"t1 first supply ->")
                .thenApplyAsync((value)-> value + "second apply - >")
                .thenApplyAsync((value)-> {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return value + "third apply - > return";
                });

        // apply作为有入参和出参的producer，作为中间状态使用
        // accept作为consumer，负责收尾
        CompletableFuture<Void> t2 = CompletableFuture
                .supplyAsync(()->"t2 method first supply ->")
                .thenApplyAsync((value)-> value + "second apply - >")
                .thenApplyAsync((value)-> value + "third apply - >")
                .thenAccept((value)->System.out.println(Thread.currentThread().getName() + ": " + value));

        // whenComplete是当前任务执行完毕后的回调函数,入参是consumer，因此返回的仍然是上一次CompleteStage complete的结果
        // handle也是回调函数，但入参是BiFunction，可以返回自定义的数据，即handle = complete + apply
        CompletableFuture<Void> t3 = CompletableFuture
                .runAsync(()->System.out.println(Thread.currentThread().getName() + ": " + "t3 run async method"))
                .whenCompleteAsync((Void,t)-> {
                    System.out.println(Thread.currentThread().getName() + ": " + "t3 complete");
                })
                .handleAsync((Void,t)->{
                    return Void;
                });

        CompletableFuture<String> t4 = CompletableFuture
                .supplyAsync(()->{
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "t4 supply method";
                })
                .whenCompleteAsync((s,t)-> {
                    System.out.println(Thread.currentThread().getName() + ": " + "t4 complete");
                });

//        System.out.println(Thread.currentThread().getName() + ": " + t1.get());
        // acceptEither方法t1、t4谁先执行完就处理谁
        t1.acceptEitherAsync(t4, System.out::println);
        t2.runAfterEitherAsync(t3, ()->{
            System.out.println("t2, t3 run after either");
        });


        // 如果要等待异步执行结果，需要使用join方法阻塞主线程类似 async/await
        t2.join();

        // Combine 与 AcceptBoth的区别为 accept为消费者 无返回值；Combine为中间态，有返回值

        // Combine等待两个CompleteFuture执行完并将其两个的结果合并返回
        CompletableFuture<String> ret = t1.thenCombineAsync(t4,(v1,v2)->{
            return v1 + v2;
        });

        // AcceptBoth等待两个CompleteFuture执行完并消费其两个的结果
        t1.thenAcceptBoth(t4, (v1,v2)->{
            System.out.printf("t1,t4 are all complete, value is %s %s",v1, v2);
        });

        // complete是查询当前任务是否完成，完成走原有的whenComplete，没完成的话中断任务，并根据入参强制完成返回
        System.out.println(t4.complete("t4 is not complete, so this method complete it."));

        // all of所有的completeFuture都完成再执行后续
        CompletableFuture.allOf(t1,t2,t3,t4).whenComplete((Void,t)->{
            System.out.println("t1,t2,t3,t4 all complete");
        });

        // any of任意一个completeFuture完成就执行后续
        CompletableFuture.anyOf(t1,t2,t3,t4).whenComplete((Void,t)->{
            System.out.println("one of t1,t2,t3,t4  complete");
        });
    }
}
