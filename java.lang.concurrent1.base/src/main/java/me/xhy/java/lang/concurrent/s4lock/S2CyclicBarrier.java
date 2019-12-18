package me.xhy.java.lang.concurrent.s4lock;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * CyclicBarrier
 *
 * CyclicBarrier 构造方法
 * 1. parties 第一个参数是参与 线程个数；
 * 2. barrierAction 第二个参数是每次线程全部达到屏障后，执行的任务；
 * 每次到达屏障，要等 barrierAction 执行结束，才继续执行下一次屏障
 *
 */
public class S2CyclicBarrier {

  public static void main(String[] args) {

    HashMap<String, Integer> result = new HashMap<>();
    Random random = new Random();

    CyclicBarrier barrier = new CyclicBarrier(6, () -> {
      System.out.println("main doing");
      result.forEach((key, value) -> System.out.println("main:" + key + "=============" + value));
      try {
        TimeUnit.SECONDS.sleep(2);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("main done");
    });

    for (int i = 0; i < 6; i++) {
      new Thread(() -> {
        try {
          System.out.println(Thread.currentThread().getName() + ": doing task1");
          int hold = random.nextInt(6);
          TimeUnit.SECONDS.sleep(hold);
          result.merge(Thread.currentThread().getName(), hold, (a,b)-> a +b);
          System.out.println(Thread.currentThread().getName() + ": task1 done, cost " + hold);
          barrier.await();

          System.out.println(Thread.currentThread().getName() + ": doing task2");
          hold = random.nextInt(6);
          TimeUnit.SECONDS.sleep(hold);
          result.merge(Thread.currentThread().getName(), hold, (a,b)-> a +b);
          System.out.println(Thread.currentThread().getName() + ": task2 done, cost " + hold);
          barrier.await();

        } catch (InterruptedException | BrokenBarrierException e) {
          e.printStackTrace();
        }
      }).start();
    }

  }
}

