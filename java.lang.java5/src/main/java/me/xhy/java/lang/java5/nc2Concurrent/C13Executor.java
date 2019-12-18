package me.xhy.java.lang.java5.nc2Concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by xuhuaiyu-macpro on 2017/3/28.
 * <p>
 * Executor ExecutorService since 1.5
 * <p>
 * 类图
 * Executor(Interface)
 * |- ExecutorService(Interface)
 * |- AbstractExecutorService(AbstractClass) ScheduleExecutorService(Interface)
 * |-ThreadPoolExecutor                   |
 * |                                   /
 * |- ScheduleThreadPoolExecutor
 * <p>
 * ExecutorService 接口扩展了 Executor 接口，增加状态控制，执行多个任务返回 Future 。
 * ScheduledExecutorService 接口，该接口是 ExecutorService 的子接口，增加了定时执行任务的功能。
 */
public class C13Executor {

  public static void main(String[] args) {

    // 执行 Runnable 任务，能看到出现相同的线程名称，表示线程被重用了
//        runTestRunnable();

    // 执行 Callable 任务
//        runTestCallable();

    // 自定义线程池
    customerThreadPool();

  }

  private static void runTestRunnable() {
    ExecutorService executorService = Executors.newCachedThreadPool();
    for (int i = 0; i < 5; i++) {
      executorService.execute(new TestRunnable());
      System.out.print("");
    }
    executorService.shutdown();
  }

  private static void runTestCallable() {
    ExecutorService executorService = Executors.newCachedThreadPool();
    List<Future<String>> resultList = new ArrayList<Future<String>>();

    // 创建10个任务并执行
    for (int i = 0; i < 10; i++) {
      // 使用ExecutorService执行Callable类型的任务，并将结果保存在future变量中
      Future<String> future = executorService.submit(new TestCallable(i));
      // 将任务执行结果存储到 List 中
      resultList.add(future);
    }

    // 遍历任务的结果
    for (Future<String> fs : resultList) {
      try {
        while (!fs.isDone()) ;//Future返回如果没有完成，则一直循环等待，直到Future返回完成
        System.out.println(fs.get());     //打印各个线程（任务）执行的结果
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        // 启动一次顺序关闭，执行以前提交的任务，但不接受新任务
        executorService.shutdown();
      }
    }
  }

  private static void customerThreadPool() {
    BlockingQueue<Runnable> bqueue = new ArrayBlockingQueue<Runnable>(20);
        /*
         * corePoolSize 最小线程数
         * maximumPoolSize 最大线程数
         * keepAliveTime 线程池中的空闲线程所能持续的最长时间
         * unit 持续时间的单位
         * workQueue 任务执行前保存任务的队列，仅保存由 execute 方法提交的 Runnable 任务
         *
         * 如果线程池中的线程数量少于 corePoolSize，即使线程池中有空闲线程，也会创建一个新的线程来执行新添加的任务；
         *
         * ThreadPoolExecutor 有多个重载方法，分别被 Executors 用于创建线程池
         *
         * newCachedThreadPool()
         * public static ExecutorService newCachedThreadPool() {
         *   return new ThreadPoolExecutor(0, Integer.MAX_VALUE,60L, TimeUnit.SECONDS,new SynchronousQueue<Runnable>());
         * }
         *
         * newFixedThreadPool()
         * public static ExecutorService newFixedThreadPool(int nThreads) {
         *   return new ThreadPoolExecutor(nThreads, nThreads,0L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>());
         * }
         *
         * newSingleThreadExecutor()
         *
         */
    ThreadPoolExecutor pool = new ThreadPoolExecutor(3, 5, 50, TimeUnit.MILLISECONDS, bqueue);

    for (int i = 0; i < 30; i++) {
      Runnable runnable = new TestCustomerThreadPool();
      try {
        pool.submit(runnable);
      } catch (Exception e) {
        System.out.println("任务超过有界队列 ： " + i);
      }
    }

    pool.shutdown();
  }

}

class TestRunnable implements Runnable {
  public void run() {
    System.out.println(Thread.currentThread().getName() + "线程被调用了。");
  }
}

class TestCallable implements Callable<String> {
  private int id;

  public TestCallable(int id) {
    this.id = id;
  }

  /**
   * 任务的具体过程，一旦任务传给 ExecutorService 的 submit 方法，
   * 则该方法自动在一个线程上执行
   */
  public String call() throws Exception {
    System.out.println(Thread.currentThread().getName() + " invoke");
    //该返回结果将被Future的get方法得到
    return Thread.currentThread().getName() + " = " + id;
  }
}

class TestCustomerThreadPool implements Runnable {
  @Override
  public void run() {
    System.out.println(Thread.currentThread().getName() + "正在执行");
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}