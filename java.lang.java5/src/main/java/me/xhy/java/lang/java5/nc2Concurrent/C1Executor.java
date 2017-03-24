package me.xhy.java.lang.java5.nc2Concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by xuhuaiyu-macpro on 2017/3/21.
 * <p>
 * Executor 和他的小伙伴们 since 1.5
 * <p>
 * 先了解一下线程池相关的 接口 和 类
 */
public class C1Executor {

    public static void main(String[] args) {

//        CachedThreadPoolDemo.test();

//        CachedThreadPoolDemoHasParam.test();

//        FixedThreadPoolDemo.test();

//        SingleThreadExecutorDemo.test();

//        UnconfigurableExecutorServiceDemo.test();
    }
}

class RunnableDemo implements Runnable {
    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName());
    }

}

class CachedThreadPoolDemo {

    public static void test() {
        // newCachedThreadPool
        // 将返回一个无限大小的线程池，使用中还要注意 内存溢出
        // 如果没有空闲线程就新建，如果有就重用
        // 线程池内的实例 60 秒过期
        // 可以使用 ThreadPoolExecutor 构造方法创建具有类似属性但细节不同（例如超时参数）的线程池。
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < 10000; i++) {
            cachedThreadPool.submit(new RunnableDemo());
        }
    }

}

class CachedThreadPoolDemoHasParam {

    static AtomicInteger seq = new AtomicInteger();

    public static void test() {
        // ThreadFactory 只实现了重命名线程名
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool(
                r -> new Thread(r, "Be given name " + seq.getAndAdd(1))
        );
        // 或者使用 Executor.defaultThreadFactory() 获得 ThreadFactory

        for (int i = 0; i < 10000; i++) {
            cachedThreadPool.submit(new RunnableDemo());
        }
    }
}

class FixedThreadPoolDemo {

    // newFixedThreadPool
    // 创建一个可重用固定线程数的线程池，以共享的无界队列方式来运行这些线程。（只的是存放任务的队列是无界的)
    public static void test() {
        ExecutorService pool = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 1000; i++) {
            pool.submit(new RunnableDemo());
        }

        pool.shutdown();

        // 此处将报错，线程池关闭后就不能再提交任务了，但是已经提交的任务将不受影响的继续执行
        pool.submit(new RunnableDemo());
    }

}

class SingleThreadExecutorDemo {

    // newSingleThreadExecutor
    // 创建一个单线程的线程池
    public static void test() {
        ExecutorService pool = Executors.newSingleThreadExecutor();

        for (int i = 0; i < 1000; i++) {
            pool.submit(new RunnableDemo());
        }

        // 先打印出来
        System.out.println("=== 提交完毕 ===");
    }
}

class UnconfigurableExecutorServiceDemo {

    public static void test() {
        ExecutorService pool = Executors.newFixedThreadPool(1);
        pool = Executors.unconfigurableExecutorService(pool);
        // 具体有说明特殊的没搞清楚，但是类似于 final 化了。
        pool = Executors.newFixedThreadPool(1);
        // 但是好像也不是
    }
}