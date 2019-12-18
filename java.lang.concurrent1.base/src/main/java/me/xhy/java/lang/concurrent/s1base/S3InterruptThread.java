package me.xhy.java.lang.concurrent.s1base;

/**
 * interrupt() 方法只是给目标线程打了一个标记
 * 如果没有代码处理中断状态，线程本身并不会理会这个标记
 * <p>
 * 动态方法和静态方法都可以放回当前线程的中断标记，但是有不同：
 * 1. 动态方法 isInterrupted 只是返回当前线程的终端标记；
 * 2. 静态方法 interrupted 带复位功能，先清除当前的中断标志 由 true 变成 false，然后发现清除前的中断标记。
 */
public class S3InterruptThread {

  public static void main(String[] args) throws InterruptedException {
    Thread t1 = new HandleInterrupt();
    Thread t2 = new NotHandleInterrupt();
    t1.start();
    t2.start();
    Thread.sleep(20);
    t1.interrupt();
    t2.interrupt();
  }

}

class HandleInterrupt extends Thread {
  private String name = "HandlInterrupt";

  @Override
  public void run() {
    String threadName = currentThread().getName();
    System.out.println(name + "interrupt flag :" + isInterrupted());
    while (!isInterrupted()) {
      System.out.println(name + " is running, interrupt flag :" + isInterrupted());
    }
    System.out.println(name + "interrupt flag :" + isInterrupted());
  }
}

class NotHandleInterrupt extends Thread {
  private String name = "NotHandleInterrupt";

  @Override
  public void run() {
    String threadName = currentThread().getName();
    System.out.println(name + "interrupt flag :" + isInterrupted());
    while (true) {
      System.out.println(name + " is running, interrupt flag :" + isInterrupted());
    }
  }
}