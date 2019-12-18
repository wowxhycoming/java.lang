package me.xhy.java.lang.java5.nc2Concurrent;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by xuhuaiyu-macpro on 2017/3/31.
 * <p>
 * since 1.5
 * <p>
 * 优先队列
 * 一个无界阻塞队列，它使用与类 PriorityQueue 相同的顺序规则，并且提供了阻塞获取操作。
 * 它本身是线程安全的，内部使用显示锁保证线程安全。
 * <p>
 * PriorityBlockingQueue 存储的对象必须是实现 Comparable 接口的。
 * 因为 PriorityBlockingQueue 队列会根据内部存储的每一个元素的 compareTo 方法比较每个元素的大小。
 * <p>
 * 应用 ： 安排任务的优先级
 */
public class C17PriorityBlockingQueue {

  public static PriorityBlockingQueue<User> queue = new PriorityBlockingQueue<User>();

  public static void main(String[] args) {
    queue.add(new User(1, "x1"));
    queue.add(new User(5, "x5"));
    queue.add(new User(23, "x23"));
    queue.add(new User(55, "x55"));
    queue.add(new User(9, "x9"));
    queue.add(new User(3, "x3"));
    for (User user : queue) {
      try {
        System.out.println(queue.take().name);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}

class User implements Comparable<User> {

  public User(int age, String name) {
    this.age = age;
    this.name = name;
  }

  int age;
  String name;

  @Override
  public int compareTo(User o) {
    return this.age > o.age ? 1 : -1;
  }
}