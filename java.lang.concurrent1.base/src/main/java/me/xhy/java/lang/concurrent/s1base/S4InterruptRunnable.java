package me.xhy.java.lang.concurrent.s1base;

/**
 * Thread 不是父类的情况下，使用 Thread.currentThread().isInterrupted() 判断中断标志
 */
public class S4InterruptRunnable {

  public static void main(String[] args) throws InterruptedException {
    UseRunnable useRunnable = new UseRunnable();
    Thread endThread = new Thread(useRunnable);
    endThread.start();
    Thread.sleep(20);
    endThread.interrupt();
  }

}

class UseRunnable implements Runnable {

  @Override
  public void run() {
    while (!Thread.currentThread().isInterrupted()) {
      System.out.println(Thread.currentThread().getName()
          + " implements Runnable.");
    }
    System.out.println(Thread.currentThread().getName()
        + " interrupt flag is " + Thread.currentThread().isInterrupted());
  }
}