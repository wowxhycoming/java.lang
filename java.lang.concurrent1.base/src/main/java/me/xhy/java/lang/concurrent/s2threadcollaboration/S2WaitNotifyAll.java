package me.xhy.java.lang.concurrent.s2threadcollaboration;

import java.util.concurrent.TimeUnit;

public class S2WaitNotifyAll {

  static final int CAPACITY = 1;

  public static void main(String[] args) {

    Clerk2 clerk = new Clerk2();
    Producer2 producer = new Producer2(clerk);
    Consumer2 consumer = new Consumer2(clerk);

    new Thread(producer, "生产者").start();
    new Thread(consumer, "消费者").start();

  }
}

/**
 * 店员
 * 针对爆仓和无货，做了wait；当货物有变动的时候，发起notifyAll，通知可以进货或卖货
 * <p>
 * 缺陷：
 * 将库存量调整为1，发现会发生 程序不能退出 的情况
 * 是因为最后一次（生产者或消费者）的wait没有被唤醒
 * 当最后一个消费者，因为无货 wait 后，
 * 生产者1进货后 notifyAll
 * 生产者2发现爆仓，wait
 * 消费者被唤醒，从wait出开始执行，不会再执行else的语句，消费者全部结束，最终剩下wait的生产者2，程序无法退出
 * <p>
 * 解决方案：去掉else可解决此问题，
 * 但是，多消费者多生产者的情况，又有新问题了
 */
class Clerk2 {
  private int product = 0;

  // 进货
  synchronized void stock() {
    if (product >= S2WaitNotifyAll.CAPACITY) {
      System.out.println(Thread.currentThread().getName() + "爆仓！");
      try {
        this.wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    } else {
      System.out.println(Thread.currentThread().getName() + " : " + ++product);
      notifyAll();
    }
  }

  synchronized void sell() {
    if (product <= 0) {
      System.out.println(Thread.currentThread().getName() + "缺货！");
      try {
        this.wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    } else {
      System.out.println(Thread.currentThread().getName() + " : " + --product);
      notifyAll();
    }
  }
}

class Producer2 implements Runnable {

  private Clerk2 clerk;

  Producer2(Clerk2 clerk) {
    this.clerk = clerk;
  }

  @Override
  public void run() {
    for (int i = 0; i < 20; i++) {
      try {
        TimeUnit.MILLISECONDS.sleep(200);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      clerk.stock();
    }
  }
}


class Consumer2 implements Runnable {

  private Clerk2 clerk;

  Consumer2(Clerk2 clerk) {
    this.clerk = clerk;
  }

  @Override
  public void run() {
    for (int i = 0; i < 20; i++) {
      clerk.sell();
    }
  }
}
