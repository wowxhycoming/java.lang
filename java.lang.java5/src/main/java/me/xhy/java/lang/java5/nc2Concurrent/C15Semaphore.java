package me.xhy.java.lang.java5.nc2Concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by xuhuaiyu-macpro on 2017/1/1.
 * <p>
 * since 1.5
 */

public class C15Semaphore extends Thread {
    private Semaphore position;
    private int id;

    public C15Semaphore(int i, Semaphore s) {
        this.id = i;
        this.position = s;
    }

    public void run() {
        try {
            //有没有空厕所
            if (position.availablePermits() > 0) { // 返回当前可用许可数
                System.out.println("顾客[" + this.id + "]进入厕所，有空位");
            } else {
                System.out.println("顾客[" + this.id + "]进入厕所，没空位，排队");
            }
            //获取到空厕所了
            position.acquire(); // 申请一个许可，可传入指定数量的许可数
            System.out.println("顾客[" + this.id + "]获得坑位");
            //使用中...
            Thread.sleep((int) (Math.random() * 10000));
            System.out.println("顾客[" + this.id + "]使用完毕");
            //厕所使用完之后释放
            position.release(); // 释放一个许可。要不要在 finally 中。
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        ExecutorService list = Executors.newCachedThreadPool();
        Semaphore position = new Semaphore(2);//只有两个厕所
        //有十个人
        for (int i = 0; i < 10; i++) {
            list.submit(new C15Semaphore(i + 1, position));
        }
        list.shutdown();
        // 通过 acquire 方法获取许可的过程是可以被中断的。
        // 如果不希望被中断，那么可以使用 acquireUninterruptibly 方法。
        // 如果当前线程被中断，那当前线程将继续等待，知道获取到许可，但是该线程的装备已经变更成中断状态，该行代码后的代码改如何？
        // 执行 还是 不执行
        // wait or await and new Thread notifyAll or single
        position.acquireUninterruptibly(2);
        System.out.println("使用完毕，需要清扫了");
        position.release(2);
    }

}
