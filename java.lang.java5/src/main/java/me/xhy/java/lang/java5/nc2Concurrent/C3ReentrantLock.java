package me.xhy.java.lang.java5.nc2Concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static me.xhy.java.lang.java5.nc2Concurrent._ThreadFactorySupport.getThreadFactory;

/**
 * Created by xuhuaiyu on 2017/3/12.
 * <p>
 * 锁
 * <p>
 * 解决多线程安全的三个方法 ：
 * 1. 同步代码块synchronized，jdk1.5以前的解决方式，隐式的锁
 * 2. 同步方法synchronized，jdk1.5以前的解决方式，隐式的锁
 * 3. 同步锁，jdk1.5提供的解决方式，现实锁，通过 lock() 上锁，通过 unlock() 解锁。
 */
public class C3ReentrantLock {

    public static void main(String[] args) {

        // 1. 多线程访问共享数据，会引发问题
        // 为什么程序不能正常退出，如何修改
//        ticket();

        // 2. 使用 Lock 解决多线程访问共享数据的问题
        lockTicket();

    }

    public static void ticket() {

        Ticket ticket = new Ticket();

        ExecutorService pool =
                Executors.newFixedThreadPool(5, getThreadFactory("窗口"));
        for(int i=0; i<5; i++) {
            pool.submit(ticket);
        }

        pool.shutdown();

    }

    public static void lockTicket() {
        LockedTicket ticket = new LockedTicket();

        ExecutorService pool =
                Executors.newFixedThreadPool(5, getThreadFactory("窗口"));
        for(int i=0; i<5; i++) {
            pool.submit(ticket);
        }

    }
}

class Ticket implements Runnable {

    private int tick = 100;

    @Override
    public void run() {
        while (true) {

            if (tick > 0) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                }

                System.out.println(Thread.currentThread().getName() + " 完成售票，余票为：" + --tick);
            }

        }
    }

}

class LockedTicket implements Runnable {

    private int tick = 100;

    // 也可使用 Atomic 解决
    private Lock lock = new ReentrantLock();

    @Override
    public void run() {

        while (true) {

            lock.lock(); // 上锁

            try {
                if (tick > 0) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                    }

                    System.out.println(Thread.currentThread().getName() + " 完成售票，余票为：" + --tick);
                }
            } finally {
                lock.unlock(); // 释放锁，必须在 finally 中
            }
        }
    }

}