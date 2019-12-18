package me.xhy.java.lang.java5.nc2Concurrent;

import java.util.Date;
import java.util.concurrent.*;

/**
 * Created by xuhuaiyu-macpro on 2017/3/28.
 */
public class C14ScheduledThreadPoolExecutor {

  public static void main(String[] args) {

    // 延迟执行
//        delay();

    // 重复执行，方法中有两个分支
//        repeat();

  }

  private static void delay() {
    ScheduledThreadPoolExecutor executor =
        (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(1);
    System.out.printf("Main: Starting at: %s\n", new Date());
    for (int i = 0; i < 5; i++) {
      TaskDelay task = new TaskDelay("Callable " + i);
      // Callable , delayTime, TimeUnit
      executor.schedule(task, i, TimeUnit.SECONDS);
            /* 为何不用 quartz ，quartz 的远景是分布式集群
             1. 一定要使用try{}catch(Throwable t){}捕获所有可能的异常，因为ScheduledThreadPoolExecutor会在任务执行遇到异常时取消后续执行。
             2. 注意scheduleAtFixedRate与scheduleWithFixedDelay的区别，
                  scheduleAtFixedRate是上一个任务开始执行之后延迟设定时间再执行，是从上一个任务开始时计时，但对于运行时长超过延迟时长的任务，会等上一个任务执行完之后，下一个任务才开始执行，此时，延时没有任何意义。
                  scheduleWithFixedDelay是在上一个任务结束执行之后延迟设定时间再执行，是从上一个任务结束时开始计算。
             */
    }
    executor.shutdown();

    try {
      executor.awaitTermination(1, TimeUnit.DAYS);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.printf("Main: Ends at: %s\n", new Date());
  }

  public static void repeat() {
    ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1);
    TaskRepetition task = new TaskRepetition();
    //1秒后开始执行任务，以后每隔2秒执行一次
    System.out.printf("Main: Starting at: %s\n", new Date());
    // 从上一个任务完成后，按重复时间延迟执行
//        executorService.scheduleWithFixedDelay(task, 1000, 2000, TimeUnit.MILLISECONDS);
    // 从上一个任务开始后，按重复时间延迟执行
    // 如果任务执行时间 比 设置的重复时间（周期）长，就要等到上一个任务执行完毕才开始执行下个任务，土鳖三真不是白叫的
    // 所以，定时任务的正确姿势是 quartz
    executorService.scheduleAtFixedRate(task, 1000, 2000, TimeUnit.MILLISECONDS);
  }
}

class TaskDelay implements Callable<String> {

  private String name;

  public TaskDelay(String name) {
    super();
    this.name = name;
  }

  @Override
  public String call() throws Exception {
    System.out.printf("%s: Starting at : %s\n", name, new Date());
    return "hello world";
  }

}


class TaskRepetition implements Runnable {

  @Override
  public void run() {
    // 如果任务的任一执行遇到异常，就会取消后续执行。否则，只能通过执行程序的取消或终止方法来终止该任务。
    // 所以，任务中的逻辑都要包裹到 try -  catch 中
    try {
      System.out.printf("任务开始 at %s\n", new Date());
      doBusiness();
      System.out.printf("任务结束 at %s\n", new Date());
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  private void doBusiness() {
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
