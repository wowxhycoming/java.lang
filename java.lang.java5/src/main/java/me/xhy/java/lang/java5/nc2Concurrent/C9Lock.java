package me.xhy.java.lang.java5.nc2Concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by xuhuaiyu-macpro on 2017/1/1.
 */


public class C9Lock implements Runnable {

    private ReentrantLockHolder lockHolder;
    private int id;

    private C9Lock(int i, ReentrantLockHolder test) {
        this.id = i;
        this.lockHolder = test;
    }

    public void run() {
        lockHolder.print(id);
    }

    public static void main(String args[]) {
        ExecutorService service = Executors.newCachedThreadPool();
        ReentrantLockHolder aHolder = new ReentrantLockHolder();
        for (int i = 0; i < 10; i++) {
            service.submit(new C9Lock(i, aHolder));
        }
        service.shutdown();
    }

}

// ReentrantLockHolder 中有一个共享的锁
class ReentrantLockHolder {
    private ReentrantLock lock = new ReentrantLock();

    void print(int str) {
        try {
            lock.lock();
            System.out.println(str + "获得");
            Thread.sleep((int) (Math.random() * 1000));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            System.out.println(str + "释放");
        }
    }

}
