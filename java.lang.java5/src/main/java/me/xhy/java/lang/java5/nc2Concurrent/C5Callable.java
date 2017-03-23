package me.xhy.java.lang.java5.nc2Concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by xuhuaiyu on 2017/3/12.
 * <p>
 * Callable since 1.5
 * <p>
 * 1. 也是一种创建线程运行的方式（Thread Runnable Callable ExecutorService）
 * 2. 有返回值，需要使用 Future(since 1.5) 的实现类接收
 */
public class C5Callable {

    public static void main(String[] args) {

        CallableDemo cd = new CallableDemo();

        // 1. 执行 Callable 方式，需要 FutureTask 实现类的支持，用于接收运算结果。
        FutureTask<Integer> result = new FutureTask<>(cd);

        // 2. 启动线程
        new Thread(result).start();

        // 3. 接收线程运算后的结果
        try {
            // Future 的 get() 方法是阻塞方法
            Integer sum = result.get();  // FutureTask 可用于 闭锁
            System.out.println(sum);
            System.out.println("------------------阻塞------------------");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }


    }
}

/**
 * 相比于 Runnable
 * <p>
 * Callable 的方法有返回值；方法会抛异常
 */
class CallableDemo implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {

        int sum = 0;

        for (int i = 0; i <= 100000; i++) {
            sum += i;
        }

        // 证明 FutureTask 的 get() 方法是阻塞方法
        Thread.sleep(1000);

        return sum;
    }

}