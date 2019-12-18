package me.xhy.java.lang.concurrent.s5future;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;


/**
 * since 1.5
 *
 * Callable<R> 是有返回值的任务，但是不能被 Thread 的构造方法接受
 * FutureTask 封装 Callable 后，传递给 Thread 可执行
 * FutureTask.get() 方法获取返回值，get() 方法是阻塞方法
 */
public class S1CallableFutureTask {

  /*实现Callable接口，允许有返回值*/
  private static class UseCallable implements Callable<Integer> {
    private int sum;

    @Override
    public Integer call() throws Exception {
      System.out.println("Callable子线程开始计算！");
      // Thread.sleep(1000); // sleep 遇到 中断，会抛出异常， FutureTask 会吞掉异常

      for (int i = 0; i < 5000; i++) {
        if (Thread.currentThread().isInterrupted()) {
          System.out.println("Callable子线程计算任务中断！");
          return null;
        }
        sum = sum + i;
        System.out.println("sum=" + sum);
      }
      System.out.println("Callable子线程计算结束！结果为: " + sum);
      return sum;
    }
  }

  public static void main(String[] args)
      throws InterruptedException, ExecutionException {

    UseCallable useCallable = new UseCallable();
    //包装
    FutureTask<Integer> futureTask = new FutureTask<>(useCallable);
    Random r = new Random();
    new Thread(futureTask).start();

    Thread.sleep(1);
    if (r.nextInt(100) > 50) {
      System.out.println("Get UseCallable result = " + futureTask.get());
    } else {
      System.out.println("Cancel................. ");
      futureTask.cancel(true); // 也是调用 interrupt 方法，要在线程中处理，才能中断
    }

  }

}
