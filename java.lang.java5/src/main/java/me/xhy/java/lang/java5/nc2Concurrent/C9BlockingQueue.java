package me.xhy.java.lang.java5.nc2Concurrent;

/**
 * Created by xuhuaiyu-macpro on 2017/3/27.
 *
 * BlockingQueue 是 Queue 的子接口
 *
 * BlockingQueue拿走元素时，如果队列为空，阻塞等待会有两种情况：
 *   一种是一直等待直到队列不为空，这种情况调用take方法， E take() throws InterruptedException;
 *   另一种就是设定一个超时时间，一直等到超时，这种情况调用的是pool方法， E poll(long timeout, TimeUnit unit) throws InterruptedException;
 *
 * 同样对于添加元素来说，也有两种情况：
 *   一直等待使用put方法，void put(E e) throws InterruptedException;
 *   超时等待使用offer方法，boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException;
 *
 * BlockingQueue的父接口 Queue 关于拿走元素的接口有两个：remove 和 pool。
 *   两者的区别在于当队列为空时前者会抛出NoSuchElementException，而后者返回null。
 * 添加元素的接口也有两个：add和offer。
 *   两者的区别在于当队列为满时前者会抛出IllegalStateException，而后者返回false。
 *
 * 总结一下：
 * Queue 定义了操作的操作是否等待。
 * BlockingQueue 更细化的定义了等待多久。
 *
 * 一般来说 Queue 类型的数据结构会有两种实现：数组和链表。
 *   对应到BlockingQueue就是ArrayBlockingQueue和LinkedBlockingQueue，两者都是基于AbstractQueue实现的。
 *
 *   public class ArrayBlockingQueue<E> extends AbstractQueue<E> implements BlockingQueue<E>, java.io.Serializable
 *   public class LinkedBlockingQueue<E> extends AbstractQueue<E> implements BlockingQueue<E>, java.io.Serializable
 *
 * AbstractQueue 只是实现了add 和remove 方法，而且很有意思的是这两个方法都是借助他们对应的无异常版本的方法 offer 和 pool 来实现的。
 */
public class C9BlockingQueue {


}
