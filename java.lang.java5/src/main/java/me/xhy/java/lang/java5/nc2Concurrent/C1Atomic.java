package me.xhy.java.lang.java5.nc2Concurrent;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by xuhuaiyu on 2017/3/11.
 *
 * AtomicInteger since 1.5
 */
public class C1Atomic {

    public static void main(String[] args) {

        // 1. i++ 工作原理
//        ipp();

        // 2. 原子性 不能保证
//        ipp10Times();

        // 3. 原子性
//        atomicIpp10Times();

        // 4. 模拟 CAS
        simulationCAS();

    }

    private static void ipp() {
        int i = 10;
        i = i++;
        System.out.println(i);
        //region // i++ 的内存步骤
        /**
         * i++ （先用 再加）
         *
         * int temp = i;
         * i = i + 1;
         * return temp;
         *
         */
        //endregion
    }

    private static void ipp10Times() {
        // i++ 包含三个步骤，读-写-改，在多线程问题中，这三步都是分离的，不是一个整体
        // 下面打印发生相同数字的数显，印证了 i++ 操作（在多线程的环境中）不是原子性操作

        IntPlusPlus ipp = new IntPlusPlus();
        for(int i=0; i< 10; i++) {
            new Thread(ipp).start();
        }
    }

    private static void atomicIpp10Times() {
        AtomicIntPlusPlus aipp = new AtomicIntPlusPlus();
        for(int i=0; i< 10; i++) {
            new Thread(aipp).start();
        }
    }

    public static void simulationCAS() {

        final SimulationCompareAndSwap cas = new SimulationCompareAndSwap();

        for (int i = 0; i < 20; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int expectedValue = cas.get();
                    boolean b = cas.compareAndSet(expectedValue, (int)(Math.random() * 100));
                    System.out.println(b);
                }
            }).start();
        }
    }

}


class IntPlusPlus implements Runnable{

    private volatile int serialNumber = 0; // volatile 并不能解决原子性问题

    @Override
    public void run() {

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
        }

        System.out.println(getSerialNumber());
    }

    public int getSerialNumber(){
        return serialNumber++;
    }


}

/**
 * AtomicInteger
 * 1. 用 volatile 保证可见性，其内部的属性都是 volatile 修饰的
 * 2. 用 CAS(Compare And Swap) 算法保证数据原子性
 *
 * CAS相关：
 * 1. CAS 算法是硬件对于并发操作（共享数据）的支持
 * 2. CAS 包含了三个操作数：
 *  (1)内存值  V
 *  (2)预估值（旧值）  A
 *  (3)更新值  B
 *  当且仅当 V == A 时， V = B; 否则，不会执行任何操作。
 *  先读取当前内存中的值 V ，读取旧值 B， 如果 V == A ， 把运算后得到的 B 更新到内存。
 *
 * 效率会高么？
 * 比 同步 和 锁 要高， 因为不会阻塞， 会利用CPU执行权重新尝试刚才的操作。
 */
class AtomicIntPlusPlus implements Runnable{

    private AtomicInteger serialNumber = new AtomicInteger();

    @Override
    public void run() {

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
        }

        System.out.println(getSerialNumber());
    }

    public int getSerialNumber(){
        return serialNumber.getAndIncrement();
    }

}

/**
 * 模拟 CAS 算法
 *
 * synchronized 是 JVM 里东西， CAS 是硬件里的东西。
 */
class SimulationCompareAndSwap {
    private int value;

    //获取内存值
    public synchronized int get(){
        return value;
    }

    //比较
    public synchronized int compareAndSwap(int expectedValue, int newValue){
        int oldValue = value;

        if(oldValue == expectedValue){
            this.value = newValue;
        }

        return oldValue;
    }

    //设置
    public synchronized boolean compareAndSet(int expectedValue, int newValue){
        return expectedValue == compareAndSwap(expectedValue, newValue);
    }
}

/**
 * 类 AtomicBoolean、AtomicInteger、AtomicLong 和 AtomicReference 的实例各自提供对相应类型单个变量的访问和更新。
 *  每个类也为该类型提供适当的实用工具方法。
 *
 * AtomicIntegerArray、AtomicLongArray 和 AtomicReferenceArray 类进一步扩展了原子操作，对这些类型的数组提供了支持。
 *  这些类在为其数组元素提供 volatile 访问语义方面也引人注目，这对于普通数组来说是不受支持的。
 *
 * 核心方法：boolean compareAndSet(expectedValue, updateValue)
 *
 * java.util.concurrent.atomic 包下提供了一些原子操作的常用类:
 * AtomicBoolean 、AtomicInteger 、AtomicLong 、 AtomicReference
 * AtomicIntegerArray 、AtomicLongArray
 * AtomicMarkableReference
 * AtomicReferenceArray
 * AtomicStampedReference
 */