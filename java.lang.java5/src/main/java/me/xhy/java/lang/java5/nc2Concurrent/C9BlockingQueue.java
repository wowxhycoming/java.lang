package me.xhy.java.lang.java5.nc2Concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by xuhuaiyu-macpro on 2017/3/27.
 * <p>
 * BlockingQueue 是 Queue 的子接口
 * <p>
 * BlockingQueue拿走元素时，如果队列为空，阻塞等待会有两种情况：
 * 一种是一直等待直到队列不为空，这种情况调用take方法， E take() throws InterruptedException;
 * 另一种就是设定一个超时时间，一直等到超时，这种情况调用的是pool方法， E poll(long timeout, TimeUnit unit) throws InterruptedException;
 * <p>
 * 同样对于添加元素来说，也有两种情况：
 * 一直等待使用put方法，void put(E e) throws InterruptedException;
 * 超时等待使用offer方法，boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException;
 * <p>
 * BlockingQueue的父接口 Queue 关于拿走元素的接口有两个：remove 和 pool。
 * 两者的区别在于当队列为空时前者会抛出NoSuchElementException，而后者返回null。
 * 添加元素的接口也有两个：add和offer。
 * 两者的区别在于当队列为满时前者会抛出IllegalStateException，而后者返回false。
 * <p>
 * 总结一下：
 * Queue 定义了操作的操作是否等待。
 * BlockingQueue 更细化的定义了等待多久。
 * <p>
 * 一般来说 Queue 类型的数据结构会有两种实现：数组和链表。
 * 对应到BlockingQueue就是ArrayBlockingQueue和LinkedBlockingQueue，两者都是基于AbstractQueue实现的。
 * <p>
 * public class ArrayBlockingQueue<E> extends AbstractQueue<E> implements BlockingQueue<E>, java.io.Serializable
 * public class LinkedBlockingQueue<E> extends AbstractQueue<E> implements BlockingQueue<E>, java.io.Serializable
 * <p>
 * AbstractQueue 只是实现了add 和remove 方法，而且很有意思的是这两个方法都是借助他们对应的无异常版本的方法 offer 和 pool 来实现的。
 * <p>
 * BlockingQueue
 * 1. 支持两个附加操作的 Queue，这两个操作是：检索元素时等待队列变为非空，以及存储元素时等待空间变得可用。
 * 2. BlockingQueue 不接受 null 元素。试图 add、put 或 offer 一个 null 元素时，某些实现会抛出 NullPointerException。
 * null 被用作指示 poll 操作失败的警戒值。
 * 3. BlockingQueue 可以是限定容量的。它在任意给定时间都可以有一个 remainingCapacity，超出此容量，便无法无阻塞地 put 额外的元素。
 * 没有任何内部容量约束的 BlockingQueue 总是报告 Integer.MAX_VALUE 的剩余容量。
 * 4. BlockingQueue 实现主要用于生产者-使用者队列，但它另外还支持 Collection 接口。因此，举例来说，使用 remove(x) 从队列中移除任意一个元素是有可能的。
 * 然而，这种操作通常不会有效执行，只能有计划地偶尔使用，比如在取消排队信息时。
 * BlockingQueue 实现是线程安全的。所有排队方法都可以使用内部锁定或其他形式的并发控制来自动达到它们的目的。
 * 然而，大量的 Collection 操作（addAll、containsAll、retainAll 和 removeAll）没有必要自动执行，除非在实现中特别说明。
 * 因此，举例来说，在只添加了 c 中的一些元素后，addAll(c) 有可能失败（抛出一个异常）。
 * 5. BlockingQueue 实质上不支持使用任何一种“close”或“shutdown”操作来指示不再添加任何项。
 * 这种功能的需求和使用有依赖于实现的倾向。例如，一种常用的策略是：对于生产者，插入特殊的 end-of-stream 或 poison 对象，
 * 并根据使用者获取这些对象的时间来对它们进行解释。
 */
public class C9BlockingQueue extends Thread {

  public static BlockingQueue<String> queue = new LinkedBlockingQueue<String>(4);
  private int index;

  public C9BlockingQueue(int i) {
    this.index = i;
  }

  public void run() {
    try {
      queue.put(String.valueOf(this.index));
      System.out.println("{" + this.index + "} in queue!");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String args[]) {
    ExecutorService service = Executors.newCachedThreadPool();
    for (int i = 0; i < 10; i++) {
      service.submit(new C9BlockingQueue(i));
    }

    Thread thread = new Thread() {
      public void run() {
        try {
          while (true) {
            Thread.sleep((int) (Math.random() * 1000));
            if (C9BlockingQueue.queue.isEmpty())
              break;
            String str = C9BlockingQueue.queue.take();
            System.out.println(str + " has take!");
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    };
    service.submit(thread);
    service.shutdown();
  }

}
