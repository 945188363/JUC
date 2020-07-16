package com.njh.JUCTest.Lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @ClassName:LockTest
 * @Author:njh
 * @Description:a test for threadLocal
 */
public class LockTest {
    public static void main(String[] args) {
        // 创建线程池
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10,10,60*10, TimeUnit.SECONDS,new LinkedBlockingDeque<>(), (r, e)->{});
        List<Integer> ll = new ArrayList<>();
        ll.add(1);
        // 可重入锁
        // @param boolean fair 是否选择公平锁
        // tryLock()与lock()的不同在于
        // tryLock()先尝试获取锁，若获取不到立即返回false，获取的到返回true；
        // 而lock()若获取不到锁，该线程会一直阻塞，直至获取到锁
        // 因此，lock()一般配合try-finally
        // tryLock()配合if语句
        Lock relock = new ReentrantLock();

        for (int i = 0; i < 10 ; i++) {
            executor.execute(()->{
                try {
                    relock.lock();
                    if (ll.stream().findFirst().isPresent()){
                        int var = ll.stream().findFirst().get();
                        ll.clear();
                        System.out.println(var);
                        ll.add(++var);
                    }
                }finally {
                    relock.unlock();
                }
            });
        }


        // 读写锁
        // 当某个Thread获取到读锁时，其他Thread可以直接获取读锁，但要等待读锁释放才可以获取写锁(shared)
        // 当某个Thread获取到写锁时，其他Thread的读写锁都要等待写锁释放
        ReadWriteLock rwLock = new ReentrantReadWriteLock();

        for (int i = 0; i < 10 ; i++) {
            executor.execute(()->{
                // 读锁
                // shared模式acquire()
                if(rwLock.readLock().tryLock()){
                    if (ll.stream().findFirst().isPresent()){
                        System.out.println(ll.stream().findFirst().get());
                    }
                }
            });
        }

        for (int i = 0; i < 10 ; i++) {
            executor.execute(()->{
                // 写锁
                if(rwLock.writeLock().tryLock()){
                    if (ll.stream().findFirst().isPresent()){
                        int var = ll.stream().findFirst().get();
                        ll.clear();
                        System.out.println(var);
                        ll.add(++var);
                    }
                }
            });
        }

    }
}
