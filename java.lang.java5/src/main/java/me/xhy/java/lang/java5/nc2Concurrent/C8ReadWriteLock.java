package me.xhy.java.lang.java5.nc2Concurrent;

import javax.swing.plaf.SliderUI;
import java.util.Random;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by xuhuaiyu on 2017/3/12.
 */
public class C8ReadWriteLock {

    public static void main(String[] args) {
        ReadWriteLockDemo demo = new ReadWriteLockDemo();

        new Thread(new Runnable() {
            @Override
            public void run() {
                demo.set(new Random(100).nextInt());
            }
        }).start();

        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    demo.get();
                }
            },String.valueOf(i)).start();
        }
    }
}

class ReadWriteLockDemo {

    ReadWriteLock lock = new ReentrantReadWriteLock();

    int number = 0;

    // 读
    public void get() {

        lock.readLock().lock(); // 只要没有些锁，读可并发，只等待了一次共同的时间

        try {
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + " : " + this.number);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.readLock().unlock();
        }

    }

    // 写
    public void set(int number) {

        try {
            Thread.sleep(1); // 为了让有些读操作可以先于写发生
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        lock.writeLock().lock();

        try {
            Thread.sleep(2000); // 持有写锁，读都等待
            this.number = number;
            System.out.println("Write : " + number);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();
        }

    }
}