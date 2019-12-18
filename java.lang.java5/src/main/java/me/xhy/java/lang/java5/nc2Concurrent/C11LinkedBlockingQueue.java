package me.xhy.java.lang.java5.nc2Concurrent;

/**
 * Created by xuhuaiyu-macpro on 2017/3/27.
 * <p>
 * LinkedBlockingQueue 使用一个链表来实现，会有一个 head 和 tail 分别指向队列的开始和队列的结尾。
 * 因此 LinkedBlockingQueue 会有两把锁，分别控制这两个元素，这样在添加元素和拿走元素的时候就不会有锁的冲突。
 * 取走元素操作的是 head ，而添加元素操作的是 tail 。
 */
public class C11LinkedBlockingQueue {
}
