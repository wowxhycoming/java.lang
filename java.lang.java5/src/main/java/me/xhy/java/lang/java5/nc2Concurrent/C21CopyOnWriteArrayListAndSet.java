package me.xhy.java.lang.java5.nc2Concurrent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by xuhuaiyu on 2017/3/12.
 * <p>
 * CopyOnWriteArrayList since 1.5
 * CopyOnWriteArraySet since 1.5
 * 在写入时赋值，解决遍历集合时对集合进行操作的问题
 * <p>
 * 每次要执行写入操作时，都会先复制一个新的列表，将新元素加入到新的列表中，然后再讲新的列表赋值给原来 Object[] 的引用
 * 由于每次操作都需要复制，添加操作多的时候就不适合使用他，他更适合做迭代多但是写入操作少的应用场景。
 * <p>
 * CopyOnWriteArraySet 是对 CopyOnWriteArrayList 的一个封装类
 * private final CopyOnWriteArrayList<E> al;
 * 区别在于因为 Set 不允许重复元素，因此 CopyOnWriteArraySet 的 add 方法调用的是 CopyOnWriteArrayList 的 addIfAbsent 方法
 */
public class C21CopyOnWriteArrayListAndSet {

  public static void main(String[] args) throws InterruptedException {

    // 1. 同步的 ArrayList 操作，在对其迭代的过程中又对其进行添加操作，会报错：并发修改异常
//        operateListSynchronized();

    Thread.sleep(100);
    // 2. 多线程 遍历时 操作 CopyOnWriteArrayList
    opertateCopyOnWriteArrayLis();
  }

  private static void operateListSynchronized() {
    SynchronizedListOperator slo = new SynchronizedListOperator();
    new Thread(slo).start();
  }

  private static void opertateCopyOnWriteArrayLis() {
    CopyOnWriteArrayListOperator cowal = new CopyOnWriteArrayListOperator();
    for (int i = 0; i < 10; i++) {
      new Thread(cowal).start();
    }
  }

}


class SynchronizedListOperator implements Runnable {

  private static List<String> list = Collections.synchronizedList(new ArrayList<String>());

  static {
    list.add("AA");
    list.add("BB");
    list.add("CC");
  }

  @Override
  public void run() {

    Iterator<String> it = list.iterator();

    while (it.hasNext()) {
      System.out.println(it.next());

      list.add("DD");
    }

  }

}

class CopyOnWriteArrayListOperator implements Runnable {

  private static CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

  static {
    list.add("=AA=");
    list.add("=BB=");
    list.add("=CC=");
  }

  @Override
  public void run() {

    Iterator<String> it = list.iterator();

    while (it.hasNext()) {
      System.out.println(it.next());

      list.add("=DD=");
    }

    System.out.println(list.size());

  }

}