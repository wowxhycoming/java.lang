package me.xhy.java.lang.java5.nc2Concurrent;

import java.util.Random;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by xuhuaiyu-macpro on 2017/3/29.
 *
 * SynchronousQueue since 1.5
 *
 * jdk1.5 阻塞算法实现，在内部采用一个锁来保证多个线程中的put()和take()方法是串行执行的。
 * 采用锁的开销是比较大的，还会存在一种情况是线程A持有线程B需要的锁，B必须一直等待A释放锁，即使A可能一段时间内因为B的优先级比较高而得不到时间片运行。
 * 所以在高性能的应用中我们常常希望规避锁的使用。
 *
 * Java 6 的 SynchronousQueue 的实现采用了一种性能更好的无锁算法 — 扩展的“Dual stack and Dual queue”算法。
 * 性能比 Java5 的实现有较大提升。
 * 竞争机制支持公平和非公平两种：非公平竞争模式使用的数据结构是后进先出栈(Lifo Stack)；公平竞争模式则使用先进先出队列（Fifo Queue），性能上两者是相当的，一般情况下，Fifo通常可以支持更大的吞吐量，但Lifo可以更大程度的保持线程的本地化。
 *
 * TODO JAVA 6 的时候在讨论
 */
public class C12SynchronousQueue {

    public static void main(String[] args) throws InterruptedException {
        final SynchronousQueue<Integer> queue = new SynchronousQueue<Integer>();
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    System.out.println("等待数据传入...");
                    System.out.println("##获取的数据为:"+queue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }}
        ).start();
        TimeUnit.SECONDS.sleep(3);//三秒之后传入数据
        System.out.println("准备传入数据..");
        queue.offer(new Random().nextInt(1000));
    }


}
