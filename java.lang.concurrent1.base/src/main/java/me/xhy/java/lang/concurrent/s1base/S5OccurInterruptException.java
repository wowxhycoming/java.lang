package me.xhy.java.lang.concurrent.s1base;

/**
 * 中断异常 对 中断标志 的影响
 * 如果发生了 InterruptedException ，中断标志 会从 true 被重制成 false
 * 如要进行中断处理，需要调用 interrupt() 自行中断
 * <p>
 * 发生异常 中断标志 不被处理，是为了留让代码有机会回收资源
 */
public class S5OccurInterruptException {

  public static void main(String[] args) throws InterruptedException {
    Thread endThread = new UseThread("OccurInterruptedException");
    endThread.start();
    Thread.sleep(500);
    endThread.interrupt();
  }

}


class UseThread extends Thread {

  public UseThread(String name) {
    super(name);
  }

  @Override
  public void run() {
    while (!isInterrupted()) {
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        System.out.println(Thread.currentThread().getName()
            + " in InterruptedException interrupt flag is "
            + isInterrupted());
        //资源释放
        interrupt();
        e.printStackTrace();
      }
      System.out.println(Thread.currentThread().getName() + " extends Thread.");
    }
    System.out.println(Thread.currentThread().getName()
        + " interrupt flag is " + isInterrupted());
  }
}