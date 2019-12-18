package me.xhy.java.lang.java5.nc2Concurrent;

import java.util.concurrent.*;

/**
 * Created by xuhuaiyu on 2017/4/13.
 * <p>
 * Callable , Future since 1.5
 * <p>
 * Callable 和 Runnable 的差别在于是否有返回值。其他用法一样。
 * <p>
 * Callable 也是创建线程运行的方式。
 * <p>
 * Callable 的执行：
 * 1. 作为线程池的参数，从而执行
 * 2. 借助 FutureTask 做转换，转换成 Thread 构造方法的参数，从而执行
 * 两种执行方式的返回值都由 Future 接收
 */
public class C22CallableAndFuture {

  public static void main(String[] args) {

    useCallable();

//        useFutureTask();

    // 异步处理发生Exception，线程一直挂起
//        hasExNoTimeout();

    // 对于主线程，不等待
//        asyncOrNot1();

    // ExecutorService 的策略是执行完毕一个任务，再去执行下一个任务。
//        asyncOrNot2();
  }

  private static void useCallable() {

    long start = System.nanoTime();

    String result = null;

    ExecutorService pool = Executors.newFixedThreadPool(8);

    Future<String> f = pool.submit(new Demo());

    try {
      result = f.get();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }

    System.out.println("result : " + result);

    long spend = (System.nanoTime() - start) / 1_000_000;

    System.out.println("spend " + spend + " ms");

    pool.shutdown();

  }

  private static void useFutureTask() {

    long start = System.nanoTime();

    String result = null;

    ExecutorService pool = Executors.newFixedThreadPool(8);

    // 也是一个 Runnable
    FutureTask<String> ft = new FutureTask<>(new Demo());

    pool.submit(ft);

    try {
      result = ft.get();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }

    System.out.println("result : " + result);

    long spend = (System.nanoTime() - start) / 1_000_000;

    System.out.println("spend " + spend + " ms");

    pool.shutdown();

  }

  private static void hasExNoTimeout() {

    ExecutorService pool = Executors.newFixedThreadPool(8);

    String result = null;
    try {
      result = pool.submit(new HasEx()).get();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }

    System.out.println(result);
    System.out.println("end");

  }

  private static void asyncOrNot1() {

    ExecutorService pool = Executors.newFixedThreadPool(2);

    System.out.println("start : " + System.currentTimeMillis() / 1000);

    for (int i = 0; i < 10; i++) {
      pool.submit(new DiffSpend(i));
    }

    pool.shutdown();

    System.out.println("end : " + System.currentTimeMillis() / 1000);

  }

  private static Future<String> call(int i, ExecutorService pool) {
    return pool.submit(new DiffSpend(i));
  }

  private static void asyncOrNot2() {

    ExecutorService pool = Executors.newFixedThreadPool(2);

    System.out.println("start : " + System.currentTimeMillis() / 1000);

    for (int i = 0; i < 10; i++) {
      call(i, pool);
    }

    pool.shutdown();

    System.out.println("end : " + System.currentTimeMillis() / 1000);

  }

}


class Demo implements Callable<String> {

  @Override
  public String call() throws Exception {
    Thread.sleep(2 * 1000);
    return "demo";
  }

}

class HasEx implements Callable<String> {

  @Override
  public String call() throws Exception {
    Thread.sleep(2 * 1000);

    return String.valueOf(1 / 0);

  }

}

class DiffSpend implements Callable<String> {

  int i = 0;

  public DiffSpend(int i) {
    this.i = i;
  }

  @Override
  public String call() throws Exception {

    Thread.sleep(i * 1000);

    System.out.println(String.valueOf(i) + " is end now " + System.currentTimeMillis() / 1000);

    return String.valueOf(i);
  }

}
