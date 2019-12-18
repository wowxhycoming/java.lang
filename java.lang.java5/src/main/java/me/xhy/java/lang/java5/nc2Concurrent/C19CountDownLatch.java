package me.xhy.java.lang.java5.nc2Concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xuhuaiyu-macpro on 2017/3/31.
 * <p>
 * 一个同步辅助类，在完成一组正在其他线程中执行的操作之前，它允许一个或多个线程一直等待。
 * 用给定的计数 初始化 CountDownLatch。由于调用了 countDown() 方法，所以在当前计数到达零之前，await 方法会一直受阻塞。
 * 之后，会释放所有等待的线程，await 的所有后续调用都将立即返回。这种现象只出现一次——计数无法被重置。
 * 如果需要重置计数，请考虑使用 CyclicBarrier。
 * <p>
 * await() 可以在多个不同线程中——多个线程等待
 * countDown() 一个线程可以多次 countDown()
 */
public class C19CountDownLatch {

  public static void main(String[] args) throws InterruptedException {
    // 开始的倒数锁
    final CountDownLatch begin = new CountDownLatch(1);
    // 结束的倒数锁
    final CountDownLatch end = new CountDownLatch(10);
    // 十名选手
    final ExecutorService exec = Executors.newFixedThreadPool(10);
    for (int index = 0; index < 10; index++) {
      final int NO = index + 1;
      Runnable run = new Runnable() {
        public void run() {
          try {
            begin.await();// 一直阻塞
            Thread.sleep((long) (Math.random() * 10000));
            System.out.println("No." + NO + " arrived");
          } catch (InterruptedException e) {
          } finally {
            end.countDown();
          }
        }
      };
      exec.submit(run);
    }
    System.out.println("Game Start");
    // 让要有线程开始
    begin.countDown();
    // 等待所有线程的 countDown
    end.await();
    System.out.println("Game Over");
    exec.shutdown();
  }
}
