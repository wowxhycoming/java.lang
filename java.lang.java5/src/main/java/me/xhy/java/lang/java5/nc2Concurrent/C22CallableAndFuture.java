package me.xhy.java.lang.java5.nc2Concurrent;

import java.util.concurrent.*;

/**
 * Created by xuhuaiyu on 2017/4/13.
 * <p>
 * Callable , Future since 1.5
 * <p>
 * Callable 和 Runnable 的差别在于是否有返回值。其他用法一样。
 * <p>
 * Callable 也是创建线程运行的方式。
 */
public class C22CallableAndFuture {

    public static void main(String[] args) {

//        useCallable();

//        useFutureTask();

    }

    private static void useCallable() {

        long start = System.nanoTime();

        String result = null;

        ExecutorService pool = Executors.newFixedThreadPool(8);

        Future<String> f = pool.submit(new Demo());

        try {
            result = f.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("result : " + result);

        long spend = (System.nanoTime() - start) / 1_000_000;

        System.out.println("spend " + spend + " ms");

        pool.shutdown();

    }

    private static void useFutureTask() {

        long start = System.nanoTime();

        String result = null;

        ExecutorService pool = Executors.newFixedThreadPool(8);

        // 也是一个 Runnable
        FutureTask<String> ft = new FutureTask<>(new Demo());

        pool.submit(ft);

        try {
            result = ft.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("result : " + result);

        long spend = (System.nanoTime() - start) / 1_000_000;

        System.out.println("spend " + spend + " ms");

        pool.shutdown();

    }

}


class Demo implements Callable<String> {

    @Override
    public String call() throws Exception {
        Thread.sleep(2 * 1000);
        return "demo";
    }

}

