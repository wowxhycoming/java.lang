package me.xhy.java.lang.concurrent.s2threadcollaboration;

import java.util.concurrent.TimeUnit;

public class S3MultiProdConsWaitNotify {

  static final int CAPACITY = 1;

  public static void main(String[] args) {

    Clerk3 clerk = new Clerk3();
    Producer3 producer = new Producer3(clerk);
    Consumer3 consumer = new Consumer3(clerk);

    // 循环次数大于1，问题出现
    for (int i = 0; i < 2; i++) {
      new Thread(producer, "生产者" + i).start();
      new Thread(consumer, "消费者" + i).start();
    }

  }
}

/**
 * 1. 消费者1： 无货 wait 释放锁
 * 2. 消费者2： 无货 wait 释放锁
 * .....
 * 3. 生产者1： 进货 notifyAll
 * 4. 消费者1 2 被唤醒，出现负库存
 * <p>
 * java doc 中 Object.wait 方法中有有这样一句话：
 * 因为有中断和虚假唤醒的可能性，所以 wait 应该总是写在 while 循环中
 * <p>
 * 意图就是，唤醒后，再做一次判断
 * 或-再次进入while中wait
 * 或-跳过while执行逻辑
 */
class Clerk3 {
  private int product = 0;

  // 进货
  synchronized void stock() {
    while/*if*/ (product >= S3MultiProdConsWaitNotify.CAPACITY) {
      System.out.println(Thread.currentThread().getName() + "爆仓！");
      try {
        this.wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    System.out.println(Thread.currentThread().getName() + " : " + ++product);
    notifyAll();
  }

  synchronized void sell() {
    while/*if*/ (product <= 0) {
      System.out.println(Thread.currentThread().getName() + "缺货！");
      try {
        this.wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    System.out.println(Thread.currentThread().getName() + " : " + --product);
    notifyAll();
  }
}

class Producer3 implements Runnable {

  private Clerk3 clerk;

  Producer3(Clerk3 clerk) {
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


class Consumer3 implements Runnable {

  private Clerk3 clerk;

  Consumer3(Clerk3 clerk) {
    this.clerk = clerk;
  }

  @Override
  public void run() {
    for (int i = 0; i < 20; i++) {
      clerk.sell();
    }
  }
}
