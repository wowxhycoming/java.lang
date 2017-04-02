package me.xhy.java.lang.java5.nc2Concurrent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xuhuaiyu-macpro on 2017/1/2.
 */

public class C20CyclicBarrier {

    // 徒步需要的时间: A, B, C, D, E
    private static int[] timeWalk = {5, 8, 15, 15, 10};
    // 自驾游
    private static int[] timeSelf = {1, 3, 4, 4, 5};
    // 旅游大巴
    private static int[] timeBus = {2, 4, 6, 6, 7};

    static String now() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(new Date()) + ": ";

    }

    static class Tour implements Runnable {

        private int[] times;
        private CyclicBarrier barrier;
        private String tourName;

        public Tour(CyclicBarrier barrier, String tourName, int[] times) {
            this.times = times;
            this.tourName = tourName;
            this.barrier = barrier;
        }

        public void run() {
            try {
                Thread.sleep(times[0] * 1000);
                System.out.println(now() + tourName + " A");
                barrier.await();
                Thread.sleep(times[1] * 1000);
                System.out.println(now() + tourName + " B");
                barrier.await();
                Thread.sleep(times[2] * 1000);
                System.out.println(now() + tourName + " C");
                barrier.await();
                Thread.sleep(times[3] * 1000);
                System.out.println(now() + tourName + " D");
                barrier.await();
                Thread.sleep(times[4] * 1000);
                System.out.println(now() + tourName + " E");
                barrier.await();
            } catch (Exception e) {
            }
        }
    }

    public static void main(String[] args) {
        // 三个旅行团
        CyclicBarrier barrier = new CyclicBarrier(3);
        // 线程数 >= 3都可，如果 < 3，就会挂起。线程不是CPU时间，不完成一项工作后是不能切换的。
        ExecutorService exec = Executors.newFixedThreadPool(3);
        exec.submit(new Tour(barrier, "WalkTour", timeWalk));
        exec.submit(new Tour(barrier, "SelfTour", timeSelf));
        // 当我们把下面的这段代码注释后，会发现，程序阻塞了，无法继续运行下去。
        exec.submit(new Tour(barrier, "BusTour", timeBus));
        exec.shutdown();
    }

}

/**
 * CyclicBarrier
 * 一个同步辅助类，它允许一组线程互相等待，直到到达某个公共屏障点 (common barrier point)。
 * 在涉及一组固定大小的线程的程序中，这些线程必须不时地互相等待，此时 CyclicBarrier 很有用。
 * 因为该 barrier 在释放等待线程后可以重用，所以称它为循环 的 barrier。
 * CyclicBarrier 支持一个可选的 Runnable 命令，在一组线程中的最后一个线程到达之后（但在释放所有线程之前），
 * 该命令只在每个屏障点运行一次。若在继续所有参与线程之前更新共享状态，此屏障操作 很有用。
 * CyclicBarrier最重要的属性就是参与者个数，另外最要方法是await()。当所有线程都调用了await()后，就表示这些线程都可以继续执行，否则就会等待。
 */