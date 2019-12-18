package me.xhy.java.lang.java5.nc2Concurrent;

/**
 * Created by xuhuaiyu-macpro on 2017/3/27.
 * <p>
 * 对于 BlockingQueue 的具体实现，主要关注的有两点：线程安全的实现和阻塞操作的实现。所以分析 ArrayBlockingQueue 也是基于这两点。
 * <p>
 * 对于线程安全来说，可以通过分析 offer() 方法和 poll() 方法就能看出线程安全是如何实现的。
 * 方法内部使用 ReentrantLock 实现，通过采用 Lock 的方式来获取锁，然后再进行插入操作，最后再释放锁。
 * 对于阻塞来说，方法内部使用 Condition 实现的。
 * 构造方法中创建了两个 Condition：
 * notEmpty = lock.newCondition();
 * notFull =  lock.newCondition();
 * 对于 put 方法
 * 当队列已满，await 方法会阻塞当前线程，并且释放 Lock，等待其他线程调用notFull的signal来唤醒这个阻塞的线程。那么这个操作必然会在拿走元素的操作中出现，这样一旦有元素被拿走，阻塞的线程就会被唤醒。
 * 这里有个问题，发出signal的线程肯定拥有这把锁的，因此await方法所在的线程肯定是拿不到这把锁的，await方法不能立刻返回，需要尝试获取锁直到拥有了锁才可以从await方法中返回。
 * 这就是阻塞的实现原理，也是所谓的线程同步。
 * <p>
 * 对于 take 方法，会用到 notEmpty 这个 Condition 。
 * <p>
 * ArrayBlockingQueue的实现相对简单，只需要一把锁就可以搞定
 */
public class C10ArrayBlockingQueue {
}
