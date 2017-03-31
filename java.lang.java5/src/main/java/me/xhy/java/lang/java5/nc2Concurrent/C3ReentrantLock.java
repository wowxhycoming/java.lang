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
//        lockTicket();

        // 3. 不适用 Lock 不能中断的情况
//        canNotInterrupt();

        // 4. 适用 Lock 解决不能中断的问题
        canInterruptable();
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

    private static void canNotInterrupt() {
        CanNotInterrupt buff = new CanNotInterrupt();

        final Writer writer = new Writer(buff);
        final Reader reader = new Reader(buff);

        writer.start();
        reader.start();

        new Thread(new Runnable() {

            @Override
            public void run() {
                long start = System.currentTimeMillis();
                for (;;) {
                    //等5秒钟去中断读
                    if (System.currentTimeMillis()
                            - start > 5000) {
                        System.out.println("不等了，尝试中断");
                        reader.interrupt();
                        break;
                    }

                }

            }
        }).start();
        // 我们期待“读”这个线程能退出等待锁，可是事与愿违，一旦读这个线程发现自己得不到锁，
        // 就一直开始等待了，就算它等死，也得不到锁，因为写线程要21亿秒才能完成，即使我们中断它，
        // 它都不来响应下，看来真的要等死了。这个时候，ReentrantLock给了一种机制让我们来响应中断，
        // 让“读”能伸能屈，勇敢放弃对这个锁的等待。我们来改写Buffer这个类，就叫BufferInterruptibly吧，可中断缓存。
    }

    private static void canInterruptable() {
        CanInterruptable buff = new CanInterruptable();

        // 两个线程传入相同的对象，他们持有的ReentrantLock对象是同一个
        final Writer2 writer = new Writer2(buff);
        final Reader2 reader = new Reader2(buff);

        writer.start();
        reader.start();

        new Thread(new Runnable() {

            @Override
            public void run() {
                long start = System.currentTimeMillis();
                for (;;) {
                    if (System.currentTimeMillis()
                            - start > 5000) {
                        System.out.println("不等了，尝试中断");
                        reader.interrupt();
                        break;
                    }
                }
            }
        }).start();

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

// 不能终止等待的情况
class CanNotInterrupt {

    private Object objLock;

    public CanNotInterrupt() {
        objLock = this;
    }

    public void write() {
        synchronized (objLock) {
            long startTime = System.currentTimeMillis();
            System.out.println("开始往这个buff写入数据…");
            for (;;) { // 模拟写入时间比等待时间长
                if (System.currentTimeMillis()
                        - startTime > 10*1000) {
                    break;
                }
            }
            System.out.println("终于写完了");
        }
    }

    public void read() {
        synchronized (objLock) {
            System.out.println("从这个buff读数据");
        }
    }


}

class Writer extends Thread {

    private CanNotInterrupt buff;

    public Writer(CanNotInterrupt buff) {
        this.buff = buff;
    }

    @Override
    public void run() {
        buff.write();
    }
}

class Reader extends Thread {

    private CanNotInterrupt buff;

    public Reader(CanNotInterrupt buff) {
        this.buff = buff;
    }

    @Override
    public void run() {

        buff.read();//这里估计会一直阻塞

        System.out.println("读结束");

    }
}

class CanInterruptable {

    private ReentrantLock lock = new ReentrantLock();

    public void write() {
        lock.lock();
        try {
            long startTime = System.currentTimeMillis();
            System.out.println("开始往这个buff写入数据…");
            for (;;) { // 写入时间无限大
                if (System.currentTimeMillis()
                        - startTime > 10*1000) {
                    break;
                }
            }
            System.out.println("终于写完了");
        } finally {
            lock.unlock();
        }
    }

    public void read() throws InterruptedException {
        System.out.println("read in");
        lock.lockInterruptibly();// 注意这里，可以响应中断。如果当前线程未被中断，则获取锁。（中断和获取锁选择先发生的）
//      lock.lock();
        System.out.println("interrupt");
        try {
            System.out.println("从这个buff读数据");
        } finally {
            lock.unlock();
        }
    }

}

class Reader2 extends Thread {

    private CanInterruptable buff;

    public Reader2(CanInterruptable buff) {
        this.buff = buff;
    }

    @Override
    public void run() {

        try {
            buff.read();//可以收到中断的异常，从而有效退出
        } catch (InterruptedException e) {
            System.out.println("我不读了");
        }

        System.out.println("读结束");

    }
}

class Writer2 extends Thread {

    private CanInterruptable buff;

    public Writer2(CanInterruptable buff) {
        this.buff = buff;
    }

    @Override
    public void run() {
        buff.write();
    }

}