package me.xhy.java.lang.concurrent.s1base;

/**
 * join 可以让多个线程顺序执行
 */
public class S6UseJoin {
  public static void main(String[] args) throws InterruptedException {

    Thread t = new Thread(new FirstLayerJoin());
    t.start();
    System.out.println("启动了子线程 FirstLayerJoin");
    t.join();
    System.out.println("main end");

  }
}

class FirstLayerJoin implements Runnable {
  @Override
  public void run() {
    System.out.println("FirstLayerJoin running");

    Thread t = new Thread(new SecondLayerJoin());
    t.start();
    System.out.println("启动了子线程 SecondLayerJoin");
    try {
      t.join();
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    System.out.println("FirstLayerJoin end");
  }
}

class SecondLayerJoin implements Runnable {
  @Override
  public void run() {
    System.out.println("SecondLayerJoin running");

    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    System.out.println("SecondLayerJoin end");

  }
}