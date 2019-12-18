package me.xhy.java.lang.concurrent.s1base;

import java.util.concurrent.TimeUnit;

/**
 * synchronized
 */
public class S8Synchronized {

  static int count = 0;
  static int syncCount = 0;
  static final Object lock = new Object();

  public static void main(String[] args) throws InterruptedException {
    for (int i = 0; i < 10; i++) {
      new Thread(new Count()).start();
      new Thread(new SyncCount()).start();
    }
    TimeUnit.SECONDS.sleep(3);
    System.out.println("count = " + count);
    System.out.println("syncCount = " + syncCount);

  }
}

class Count extends Thread {
  @Override
  public void run() {
    for (int i = 0; i < 10000; i++) {
      S8Synchronized.count++;
    }
  }
}


class SyncCount extends Thread {
  @Override
  public void run() {
    synchronized (S8Synchronized.lock) {
      for (int i = 0; i < 10000; i++) {
        S8Synchronized.syncCount++;
      }
    }
  }
}
