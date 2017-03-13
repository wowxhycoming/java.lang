package me.xhy.java.lang.java5.nc2Concurrent;

/*
 * 生产者和消费者案例
 */
public class C7_1ProducerAndConsumer {

    public static void main(String[] args) {

//        onlySyncMehotd();

//        waitAndNotify();

//        noNotifyOnePair();

//        noNotifyMorePairs();

        theRightWayOfWait();
    }

    // 只在共享类中的 访问共享数据的方法上进行同步
    private static void onlySyncMehotd() {

        OnlySyncMethod onlySyncMehotd = new OnlySyncMethod();

        onlySyncMehotd.test();

    }

    private static void waitAndNotify() {

        WaitAndNotify waitAndNotify = new WaitAndNotify();

        waitAndNotify.test();

    }

    private static void noNotifyOnePair() {
        NoNotify onNotify = new NoNotify();

        onNotify.testOnePair();
    }

    private static void noNotifyMorePairs() {
        NoNotify onNotify = new NoNotify();

        onNotify.testMorePairs();
    }

    private static void theRightWayOfWait() {
        TheRightWayOfWait theRightWayOfWait = new TheRightWayOfWait();

        theRightWayOfWait.testMorePairs();
    }

}

// 没有使用 等待-唤醒 机制
class OnlySyncMethod {

    public void test() {

        Clerk clerk = new Clerk();

        // 都访问共享数据
        Producer producer = new Producer(clerk);
        Consumer consumer = new Consumer(clerk);

        new Thread(producer, "生产者").start();
        new Thread(consumer, "消费者").start();

    }

    // 店员
    class Clerk {
        // 商品，设定为 ： 最大10，不可进货；最少0不可卖货。
        private int product = 0;

        // 进货
        public synchronized void get() {
            if (product >= 10) {
                System.out.println("商品已满！");
            } else {
                System.out.println(Thread.currentThread().getName() + " : " + ++product);
            }
        }

        public synchronized void sale() {
            if (product <= 0) {
                System.out.println("缺货！");
            } else {
                System.out.println(Thread.currentThread().getName() + " : " + --product);
            }
        }
    }

    class Producer implements Runnable {

        // 持有店员，不能自己 new ，要访问共享数据，要不就是毫不相干的多个店员，不会有冲突。使用构造器传入共享数据。
        Clerk clerk;

        public Producer(Clerk clerk) {
            this.clerk = clerk;
        }

        // 进货
        @Override
        public void run() {
            for (int i = 0; i < 20; i++) {
                clerk.get();
            }
        }
    }

    class Consumer implements Runnable {

        Clerk clerk;

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

}

class WaitAndNotify {

    public void test() {

        WaitAndNotify.Clerk clerk = new WaitAndNotify.Clerk();

        // 都访问共享数据
        WaitAndNotify.Producer producer = new WaitAndNotify.Producer(clerk);
        WaitAndNotify.Consumer consumer = new WaitAndNotify.Consumer(clerk);

        new Thread(producer, "生产者").start();
        new Thread(consumer, "消费者").start();

    }

    // 店员
    class Clerk {
        // 商品，设定为 ： 最大10，不可进货；最少0不可卖货。
        private int product = 0;

        // 进货
        public synchronized void get() {
            if (product >= 10) {
                System.out.println("商品已满！");

                try {
                    // 商品已满，就不再生产
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println(Thread.currentThread().getName() + " : " + ++product);

                // 当有数据被产生时（有可用数据）
                this.notifyAll();
            }
        }

        public synchronized void sale() {
            if (product <= 0) {
                System.out.println("缺货！");

                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println(Thread.currentThread().getName() + " : " + --product);

                // 有空位了（可容）
                this.notifyAll();
            }
        }
    }

    class Producer implements Runnable {

        Clerk clerk;

        public Producer(Clerk clerk) {
            this.clerk = clerk;
        }

        // 进货
        @Override
        public void run() {
            for (int i = 0; i < 20; i++) {
                clerk.get();
            }
        }
    }

    class Consumer implements Runnable {

        Clerk clerk;

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

}

/**
 * 程序为什么不能结束
 * <p>
 * 因为卡在了 wait() 上，没有线程去唤醒他
 * <p>
 * 生产者有 200ms 的延迟 ， 消费者会更快一些。
 * 问题出在 get 和 sale 方法的 else 上
 * 商品满仓 或 商品空仓 的时候，只做了等待，但是等待结束后，什么都不做就结束了一个线程
 * 将 else 去掉，即解决问题，没次等待结束都回去唤醒 其他等待
 */

/**
 * 当有多对 生产者-消费者 的时候
 * <p>
 * 改进后的程序也不能正常运行，是因为存在 虚假唤醒 的情况
 * <p>
 * 消费者较快， 会有两个线程同时停留到 wait() 方法上
 * 当生产者发起一次 notifyAll() 后，两个消费者同时执行 --product ， 所以出现负数
 * <p>
 * 在 JDK API 中， wait() 方法中有一句话
 * As in the one argument version, interrupts and spurious wakeups are possible, and this method should always be used in a loop:
 * 中断 和 虚假唤醒 是可能的，因此这个方法应该总是使用在循环中。如同：
 * synchronized (obj) {
 * while (<conditionList does not hold>)
 * obj.wait();
 * // Perform action appropriate to conditionList
 * }
 * 就是在 wait() 被唤醒的时候， 再进行一次判断， 决定继续 wait() 还是 向下执行。
 */
class NoNotify {

    public void testOnePair() {

        NoNotify.Clerk clerk = new NoNotify.Clerk();

        // 都访问共享数据
        NoNotify.Producer producer = new NoNotify.Producer(clerk);
        NoNotify.Consumer consumer = new NoNotify.Consumer(clerk);

        new Thread(producer, "生产者").start();
        new Thread(consumer, "消费者").start();

    }

    public void testMorePairs() {

        NoNotify.Clerk clerk = new NoNotify.Clerk();

        // 都访问共享数据
        NoNotify.Producer producer = new NoNotify.Producer(clerk);
        NoNotify.Consumer consumer = new NoNotify.Consumer(clerk);

        new Thread(producer, "生产者-A").start();
        new Thread(consumer, "消费者-B").start();

        new Thread(producer, "生产者-C").start();
        new Thread(consumer, "消费者-D").start();

    }

    // 店员
    class Clerk {
        // 商品，设定为 ： 最大1，不可进货；最少0不可卖货。
        private int product = 0;

        // 进货
        public synchronized void get() {
            // 让线程交替执行
            if (product >= 1) {
                System.out.println("商品已满！");

                try {
                    // 商品已满，就不再生产
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
//            else {
            System.out.println(Thread.currentThread().getName() + " : " + ++product);

            // 当有数据被产生时（有可用数据）
            this.notifyAll();
//            }
        }

        public synchronized void sale() {
            if (product <= 0) {
                System.out.println("缺货！！");

                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
//            else {
            System.out.println(Thread.currentThread().getName() + " : " + --product);

            // 有空位了（可容）
            this.notifyAll();
//            }
        }
    }

    class Producer implements Runnable {

        Clerk clerk;

        public Producer(Clerk clerk) {
            this.clerk = clerk;
        }

        // 进货
        @Override
        public void run() {
            for (int i = 0; i < 20; i++) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                clerk.get();
            }
        }
    }

    class Consumer implements Runnable {

        Clerk clerk;

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

}


class TheRightWayOfWait {

    public void testMorePairs() {

        TheRightWayOfWait.Clerk clerk = new TheRightWayOfWait.Clerk();

        // 都访问共享数据
        TheRightWayOfWait.Producer producer = new TheRightWayOfWait.Producer(clerk);
        TheRightWayOfWait.Consumer consumer = new TheRightWayOfWait.Consumer(clerk);

        new Thread(producer, "生产者-A").start();
        new Thread(consumer, "消费者-B").start();

        new Thread(producer, "生产者-C").start();
        new Thread(consumer, "消费者-D").start();

    }

    // 店员
    class Clerk {
        // 商品，设定为 ： 最大1，不可进货；最少0不可卖货。
        private int product = 0;

        // 进货
        public synchronized void get() {
            // 让线程交替执行
            while (product >= 1) {
                System.out.println(Thread.currentThread().getName() + " : " + "商品已满！");

                try {
                    // 商品已满，就不再生产
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
//            else {
            System.out.println(Thread.currentThread().getName() + " : " + ++product);

            // 当有数据被产生时（有可用数据）
            this.notifyAll();
//            }
        }

        public synchronized void sale() {
            while (product <= 0) {
                System.out.println(Thread.currentThread().getName() + " : " + "缺货！！");

                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
//            else {
            System.out.println(Thread.currentThread().getName() + " : " + --product);

            // 有空位了（可容）
            this.notifyAll();
//            }
        }
    }

    class Producer implements Runnable {

        Clerk clerk;

        public Producer(Clerk clerk) {
            this.clerk = clerk;
        }

        // 进货
        @Override
        public void run() {
            for (int i = 0; i < 20; i++) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                clerk.get();
            }
        }
    }

    class Consumer implements Runnable {

        Clerk clerk;

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

}