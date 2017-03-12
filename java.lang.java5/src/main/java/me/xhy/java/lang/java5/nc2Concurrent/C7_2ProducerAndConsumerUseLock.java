package me.xhy.java.lang.java5.nc2Concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * 生产者消费者案例：
 */
public class C7_2ProducerAndConsumerUseLock {

    public static void main(String[] args) {
        Clerk clerk = new Clerk();

        Producer pro = new Producer(clerk);
        Consumer con = new Consumer(clerk);

        new Thread(pro, "生产者 A").start();
        new Thread(con, "消费者 B").start();

//       new Thread(pro, "生产者 C").start();
//       new Thread(con, "消费者 D").start();
    }

}

class Clerk {
    private int product = 0;

    private Lock lock = new ReentrantLock();
    // 获取 Condition
    private Condition condition = lock.newCondition();

    // 进货
    public void get() {
        lock.lock();

        try {
            if (product >= 1) { // 为了避免虚假唤醒，应该总是使用在循环中。
                System.out.println(Thread.currentThread().getName() + " : " + "产品已满！");

                try {
                    condition.await(); // 使用 lock 的等待方式
                } catch (InterruptedException e) {
                }

            }
            System.out.println(Thread.currentThread().getName() + " : " + ++product);

            condition.signalAll(); // 使用 lock 的唤醒方式
        } finally {
            lock.unlock();
        }

    }

    // 卖货
    public void sale() {
        lock.lock();

        try {
            if (product <= 0) {
                System.out.println(Thread.currentThread().getName() + " : " + "缺货！");

                try {
                    condition.await();
                } catch (InterruptedException e) {
                }
            }

            System.out.println(Thread.currentThread().getName() + " : "
            + --product);

            condition.signalAll();

        } finally {
            lock.unlock();
        }
    }
}

// 生产者
class Producer implements Runnable {

    private Clerk clerk;

    public Producer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            clerk.get();
        }
    }
}

// 消费者
class Consumer implements Runnable {

    private Clerk clerk;

    public Consumer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            clerk.sale();
        }
    }

}