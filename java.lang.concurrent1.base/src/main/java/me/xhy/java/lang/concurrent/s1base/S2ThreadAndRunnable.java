package me.xhy.java.lang.concurrent.s1base;

import java.util.concurrent.ExecutionException;

/**
 * Thread 是对线程的抽象，Runnable 是对任务的抽象
 * 一个线程只能执行一次，并且线程是有状态的，多次执行会报错
 */
public class S2ThreadAndRunnable {
  /*扩展自Thread类*/
  private static class UseThread extends Thread {
    @Override
    public void run() {
      super.run();
      System.out.println("extends Thread");
    }
  }


  /*实现Runnable接口*/
  private static class UseRunnable implements Runnable {

    @Override
    public void run() {
      System.out.println("implements Runnable");
    }
  }


  public static void main(String[] args) throws InterruptedException, ExecutionException {
    UseThread useThread = new UseThread();
    useThread.start();
    // useThread.start();
    /* 这里会报错
    一个 Thread 只能执行一次，并且线程是有状态的
     */

    UseRunnable useRunnable = new UseRunnable();
    new Thread(useRunnable).start();


  }
}
