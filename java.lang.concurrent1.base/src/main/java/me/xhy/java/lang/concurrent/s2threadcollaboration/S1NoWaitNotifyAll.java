package me.xhy.java.lang.concurrent.s2threadcollaboration;

public class S1NoWaitNotifyAll {

  static final int CAPACITY = 10;

  public static void main(String[] args) {

    Clerk1 clerk = new Clerk1();
    Producer1 producer = new Producer1(clerk);
    Consumer1 consumer = new Consumer1(clerk);

    new Thread(producer, "生产者").start();
    new Thread(consumer, "消费者").start();

  }
}


/**
 * synchronized 锁住的是什么
 * 1. 动态方法（实力方法） 锁是实例，也就是一个实例内所有的动态方法持有相同的锁
 * 2. 静态方法 锁是类对象 所有的静态方法、实例的静态方法 持有同一把锁，与动态方法的锁不同
 */

/**
 * 店员
 * product 为商品数量，设定为最大是10，不可进货；最少是0，不可卖货。
 * 多线程调用 stock 或 sell ，共享方法，存在线程安全问题，方法上加 synchronized
 * 同一时间只有 一个stock执行、一个sell执行
 * stock 和 sell 都访问 product ，共享变量，存现线程安全问题 ，基础数据类型不能加锁
 * 缺陷：
 * 1. 不能分离 stock 和 sell 访问共享变量。
 * 2. 爆仓还进，无货还卖。
 */
class Clerk1 {
  private int product = 0;

  // 进货
  synchronized void stock() {
    if (product >= S1NoWaitNotifyAll.CAPACITY) {
      System.out.println("爆仓！");
    } else {
      System.out.println(Thread.currentThread().getName() + " : " + ++product);
    }
  }

  synchronized void sell() {
    if (product <= 0) {
      System.out.println("缺货！");
    } else {
      System.out.println(Thread.currentThread().getName() + " : " + --product);
    }
  }
}

class Producer1 implements Runnable {

  private Clerk1 clerk;

  Producer1(Clerk1 clerk) {
    this.clerk = clerk;
  }

  @Override
  public void run() {
    for (int i = 0; i < 20; i++) {
      clerk.stock();
    }
  }
}


class Consumer1 implements Runnable {

  private Clerk1 clerk;

  Consumer1(Clerk1 clerk) {
    this.clerk = clerk;
  }

  @Override
  public void run() {
    for (int i = 0; i < 20; i++) {
      clerk.sell();
    }
  }
}
