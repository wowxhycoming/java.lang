package me.xhy.java.lang.java5.nc1Keyword;

import me.xhy.java.lang.java5.nc2Concurrent.C1Atomic;

/**
 * Created by xuhuaiyu on 2017/3/11.
 * <p>
 * 把 volatile 视为 java 5 版本的关键字，是因为 volatile 在 java 5 之后才发挥作用，在之前版本会引发莫名其妙的问题。
 *
 * 下面测试代码中涉及到原子性问题的，参照 ：
 * @see C1Atomic
 */
public class C1Volatile {

    public static void main(String[] args) {
        //region // 为什么没有打印横线
        /**
         * 程序流程：
         * 1. 读线程先执行，并且先读到的 flag = false
         * 2. 读线程一直等待 flag = true
         * 3. 写线程设置 flag = true
         * 4. 读线程并没有读取到
         *
         * 线索：
         * JVM 会为每一个线程分配一个独立的缓存（CPU高速缓存），用于挺高效率。
         * 两个线程共同访问同一个对象中的变量。
         *
         * 内存流程：
         * 当程序运行以后，主存(heap物理内存)中的 flag = false，该值最先被读线程获取，并缓存到自己的线程中（寄存器）。
         * 写线程后启动，先将 flag = false 读到自己的线程中，更改值，再更新回主存。
         *
         * 原因：
         * 由于 while(true) 是 java 的底层机制，效率非常高，高到读线程没有机会去主存读取数据，而一直读取本地的缓存。
         *
         * 内存可见性为题：
         * 多个线程在操作共享数据的时候，多个线程的之间的操作是彼此不可见的。
         *
         * 解决问题：
         * 可以在 while(true) 中增加同步锁，能保证每次都刷新缓存。
         * 但是，枷锁是效率最低的解决办法，如果有多个线程同时访问这段代码，会有线程挂起。
         * 这时，volatile 关键字就可以起作用了。
         */
         //endregion
        new Thread(new ThreadDemoPropertyReader()).start();
        // region // volatile
        /**
         * volatile 关键字可以保证多个线程操作共享数据时，线程间彼此的操作是可见的。
         *
         * volatile 关键字修饰的变量，在底层依靠内存栅栏实时刷新。可以简单理解成，多个线程操作共享数据都在主存中完成。
         *
         * volatile 提供的解决方案会比 synchronized 效率高。
         *
         * volatile 修饰的关键字，将不会被 JVM 重排序(一种优化方案)。
         */
        // endregion
        new Thread(new ThreadDemoVolatilePropertyReader()).start();
        // region // volatile 和 synchronized 的比较
        /**
         * 1. volatile 不具有互斥性 （synchronized 互斥锁）
         * 2. volatile 不能保证变量的原子性
         */
        // endregion

        // region // i = i + 1 内存
        /**
         * 1. 当线程执行这个语句时，会先从主存当中读取i的值，
         * 2. 然后复制一份到高速缓存当中，然后CPU执行指令对i进行加1操作
         * 3. 然后将数据写入高速缓存，最后将高速缓存中i最新的值刷新到主存当中
         */
        // endregion

        // region 缓存问题解决 和 并发关键字
        /**
         * 为了解决缓存不一致性问题，通常来说有以下2种解决方法：
         * 1. 通过在总线加LOCK#锁的方式
         * 2. 通过缓存一致性协议
         * 这2种方式都是硬件层面上提供的方式
         *
         * 在并发编程中，我们通常会遇到以下三个问题：原子性问题，可见性问题，有序性问题。
         * 1. 原子性：即一个操作或者多个操作 要么全部执行并且执行的过程不会被任何因素打断，要么就都不执行。、
         *  经典的转账案例
         * 2. 可见性：是指当多个线程访问同一个变量时，一个线程修改了这个变量的值，其他线程能够立即看得到修改的值。
         *  上面的例子
         * 3. 有序性：即程序执行的顺序按照代码的先后顺序执行。
         *  举个简单的例子
         *  x = 10;        //语句1
         *  y = x;         //语句2
         *  x++;           //语句3
         *  x = x + 1;     //语句4
         *      可能的执行顺序： 2 1 3 4
         */
        //endregion
    }

}

class ThreadDemoPropertyReader implements Runnable {

    @Override
    public void run() {
        ThreadDemo td = new ThreadDemo();
        new Thread(td).start();

        while(true){
//            synchronized (td) {
                if (td.isFlag()) {
                    System.out.println("ThreadDemoPropertyReader------------------");
                    break;
                }
//            }
        }
    }

}

class ThreadDemo implements Runnable {

    private boolean flag = false;

    @Override
    public void run() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }

        flag = true;

        System.out.println("ThreadDemo flag=" + isFlag());

    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}

class ThreadDemoVolatilePropertyReader implements Runnable {

    @Override
    public void run() {
        ThreadDemoVolatile tdv = new ThreadDemoVolatile();
        new Thread(tdv).start();

        while(true){
            if(tdv.isFlag()){
                System.out.println("ThreadDemoVolatilePropertyReader------------------");
                break;
            }
        }
    }

}

class ThreadDemoVolatile implements Runnable {

    private volatile boolean flag = false;

    @Override
    public void run() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }

        flag = true;

        System.out.println("ThreadDemoVolatile flag=" + isFlag());

    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

}