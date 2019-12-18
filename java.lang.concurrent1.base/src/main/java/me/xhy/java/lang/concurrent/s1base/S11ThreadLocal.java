package me.xhy.java.lang.concurrent.s1base;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 */
public class S11ThreadLocal {

  private static Store s = new Store();
  // 每次初始化返都返回一个相同的引用，引用保存着++的历史，不会创建“副本”
  static ThreadLocal<Store> tl = ThreadLocal.withInitial(() -> s);
  // 每次初始化，返回一个相同的数字 0 ，每次都从0开始++
  static ThreadLocal<Integer> tli = ThreadLocal.withInitial(() -> 0);

  public static void main(String[] args) throws InterruptedException {
    for (int i = 0; i < 3; i++) {
      new ReferenceWorker(i).start();
    }

    TimeUnit.SECONDS.sleep(10);
    System.out.println("==============");
    for (int i = 0; i < 3; i++) {
      new IntegerWorker(i).start();
    }
  }

  static class ReferenceWorker extends Thread {
    ReferenceWorker(int i) {
      super("Thread" + i + ": ");
    }

    @Override
    public void run() {
      int holdTime = new Random().nextInt(10);
      System.out.println(getName() + " holdTime = " + holdTime);
      try {
        TimeUnit.SECONDS.sleep(holdTime);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      Store s = tl.get();
      for (int j = 0; j < 1000; j++) {
        s.setCount(s.getCount() + 1);
      }
      System.out.println(getName() + " tl hashCode = " + tl.hashCode() + "; store hashCode = " + s.hashCode());
      System.out.println(getName() + s.getCount());
      tl.set(s);
      System.out.println(getName() + tl.get().getCount());
    }
  }

  static class IntegerWorker extends Thread {
    static AtomicInteger ai = new AtomicInteger(0);

    IntegerWorker(int i) {
      super("Thread" + i + ": ");
    }

    @Override
    public void run() {
      int holdTime = new Random().nextInt(10);
      System.out.println(getName() + " holdTime = " + holdTime);
      try {
        TimeUnit.SECONDS.sleep(holdTime);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      ai.getAndIncrement();
      System.out.println(ai.get());
      int s = tli.get();
      for (int j = 0; j < 1000; j++) {
        s++;
      }
      tli.set(s);
      System.out.println(getName() + tli.get());
    }
  }
}


class Store {
  private int count = 0;

  int getCount() {
    return count;
  }

  void setCount(int count) {
    this.count = count;
  }
}

