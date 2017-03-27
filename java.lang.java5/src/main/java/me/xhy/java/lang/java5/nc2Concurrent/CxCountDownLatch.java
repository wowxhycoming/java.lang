package me.xhy.java.lang.java5.nc2Concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * Created by xuhuaiyu on 2017/3/12.
 * <p>
 * CountDownLatch since 1.5
 * 闭锁，在完成某些运算时，只有其他所有线程的运算全部完成，当前运算才继续执行
 */
public class CxCountDownLatch {

    public static void main(String[] args) {
        final CountDownLatch latch = new CountDownLatch(50);
        LatchDemo ld = new LatchDemo(latch);

        long start = System.currentTimeMillis();

        for (int i = 0; i < 50; i++) {
            new Thread(ld).start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
        }

        long end = System.currentTimeMillis();

        System.out.println("耗费时间为：" + (end - start));
    }

}

class LatchDemo implements Runnable {

    private CountDownLatch latch;

    public LatchDemo(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {

        try {
            for (int i = 0; i < 50000; i++) {
                if (i % 2 == 0) {
                    System.out.println(i);
                }
            }
        } finally {
            latch.countDown(); // latch.countDown() 方法必须执行，必须放到 finally 中
        }

    }

}