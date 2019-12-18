package me.xhy.java.lang.concurrent.s4lock;

import java.time.Instant;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 锁
 * <p>
 * 解决多线程安全的三个方法 ：
 * 1. 同步代码块synchronized，jdk1.5以前的解决方式，隐式的锁
 * 2. 同步方法synchronized，jdk1.5以前的解决方式，隐式的锁
 * 3. 同步锁，jdk1.5提供的解决方式，现实锁，通过 lock() 上锁，通过 unlock() 解锁。
 * <p>
 * ReentrantLock 闭锁
 * 1. 初始化扣除点的个数
 * 2. 当有任务完成够，调用 lock.countDown() 进行扣减
 * 3. 等待锁的对象在 lock.await() 处阻塞等待
 * 4. 当 lock 被扣减成 0，可以获得锁资源
 * <p>
 * 一个任务可以多次 countDown() 同一个锁， 多个任务可以同时 await() 同一个锁
 */
public class S1ReentrantLock {

  public static void main(String[] args) throws InterruptedException {
    CountDownLatch lock = new CountDownLatch(6);
    System.out.println(Thread.currentThread().getName() + ": 主线程运行，将在 await() 出阻塞，当前时间 : " + Instant.now());

    // 该线程等待 lock
    new Thread(() -> {
      try {
        lock.await();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println(Thread.currentThread().getName() + ": got it");
    }, "the other await thread").start();

    // 每次任务执行完毕 countDown 一次
    for (int i = 0; i < 4; i++) {
      new Thread(() -> {
        System.out.println(Thread.currentThread().getName() + ": 工作开始");

        try {
          TimeUnit.SECONDS.sleep(new Random(3).nextInt());
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

        lock.countDown();
        System.out.println(Thread.currentThread().getName() + ": 工作结束");
      }).start();

    }

    // 任务完成，countDown 两次
    new Thread(() -> {
      System.out.println(Thread.currentThread().getName() + ": 工作开始");

      try {
        TimeUnit.SECONDS.sleep(new Random(5).nextInt());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      lock.countDown();
      System.out.println(Thread.currentThread().getName() + ": 里程碑工作结束");
      lock.countDown();
      System.out.println(Thread.currentThread().getName() + ": 工作结束");

    }, "count down twice").start();

    lock.await();
    System.out.println(Thread.currentThread().getName() + ": 主线程运行，度过阻塞，持有锁，当前时间 : " + Instant.now());

  }
}