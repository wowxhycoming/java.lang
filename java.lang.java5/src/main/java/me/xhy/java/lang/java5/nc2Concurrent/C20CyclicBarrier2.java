package me.xhy.java.lang.java5.nc2Concurrent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;

public class C20CyclicBarrier2 {

  static CyclicBarrier barrier = new CyclicBarrier(4, new CollectThread());

  private static ConcurrentHashMap<String, Long> resultMap
      = new ConcurrentHashMap<>();//存放子线程工作结果的容器

  public static void main(String[] args) {
    for (int i = 0; i <= 4; i++) {
      Thread thread = new Thread(new SubThread());
      thread.start();
    }

  }

  private static class CollectThread implements Runnable {

    @Override
    public void run() {
      StringBuilder result = new StringBuilder();
      for (Map.Entry<String, Long> workResult : resultMap.entrySet()) {
        result.append("[" + workResult.getValue() + "]");
      }
      System.out.println(" the result = " + result);
      System.out.println("do other business........");
    }
  }

  private static class SubThread implements Runnable {

    @Override
    public void run() {
      long id = Thread.currentThread().getId();
      resultMap.put(Thread.currentThread().getId() + "", id);
      try {
        Thread.sleep(1000 + id);
        System.out.println("Thread_" + id + " ....do something ");
        barrier.await(); // 每次屏障重制，执行任务
        Thread.sleep(1000 + id);
        System.out.println("Thread_" + id + " ....do its business ");
        barrier.await(); // 每次屏障重制，执行任务
      } catch (Exception e) {
        e.printStackTrace();
      }

    }
  }

}


/**
 * CountDownLatch 和 CyclicBarrier 的区别
 * CountDownLatch 只能使用一次， CyclicBarrier 可以重复使用
 * CountDownLatch 的计数和线程数之间没关系， CyclicBarrier 的计数要与线程数相等
 */