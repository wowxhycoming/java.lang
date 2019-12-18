package me.xhy.java.lang.concurrent.s6CASAtomic;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * Created by xuhuaiyu on 2017/3/11.
 * <p>
 * AtomicXXX since 1.5
 * <p>
 * 通过 AtomicInteger 初步了解
 */
public class S1Atomic {

  public static void main(String[] args) {

    // 1. i++ 工作原理
//        ipp();

    // 2. 原子性 不能保证
//        ipp10Times();

    // 3. 原子性
//        atomicIpp10Times();

    // 4. 模拟 CAS
//        simulationCAS();

    // 5. atomic 只能保证一个操作的原子性，不能保证多个操作的原子性
    // 最终的运算结果一定是正确的，因为 Atomic 提供了保障。
    // 但是每个线程的输出不一定都是整10倍，是因为方法内的多个 Atomic 操作不是原子的。
//        add10();

    // 6. AtomicIntegerArray 和 int[] 是两个完全不同的对象，元素组不会发生变化
//    UseAtomicIntegerArray uia = new UseAtomicIntegerArray();
//    uia.test();

    // 7. AtomicReference
    UserAtomicReference uar = new UserAtomicReference();
    uar.test();


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

    for (int i = 0; i < 20; i++) {
      new Thread(ipp).start();
    }

  }

  private static void atomicIpp10Times() {
    AtomicIntPlusPlus aipp = new AtomicIntPlusPlus();
    for (int i = 0; i < 10; i++) {
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
          boolean b = cas.compareAndSet(expectedValue, (int) (Math.random() * 100));
          System.out.println(b);
        }
      }).start();
    }
  }

  public static void add10() {
    MultiAtomicOption multiAtomicOption = new MultiAtomicOption();
    for (int i = 0; i < 1000; i++) {
      new Thread(multiAtomicOption, String.valueOf(i)).start();
    }
  }

}


class IntPlusPlus implements Runnable {

  private volatile int serialNumber = 0; // volatile 并不能解决原子性问题

  @Override
  public void run() {

    try {
      Thread.sleep(200);
    } catch (InterruptedException e) {
    }

    System.out.println(getSerialNumber());
  }

  public int getSerialNumber() {
    return serialNumber++;
  }

}

/**
 * AtomicInteger
 * 1. 用 volatile 保证可见性，其内部的属性都是 volatile 修饰的
 * 2. 用 CAS(Compare And Swap) 算法保证数据原子性
 * <p>
 * CAS相关：
 * 1. CAS 算法是硬件对于并发操作（共享数据）的支持
 * 2. CAS 包含了三个操作数：
 * (1)内存值  V
 * (2)预估值（旧值）  A
 * (3)更新值  B
 * 当且仅当 V == A 时， V = B; 否则，不会执行任何操作。
 * 先读取当前内存中的值 V ，读取旧值 B， 如果 V == A ， 把运算后得到的 B 更新到内存。
 * <p>
 * 效率会高么？
 * 比 同步 和 锁 要高， 因为不会阻塞， 会利用CPU执行权重新尝试刚才的操作。
 * <p>
 * CAS的好处：
 * 操作系统级别的支持，效率更高，无锁机制，降低线程的等待，实际上是把这个任务丢给了操作系统来做。
 * <p>
 * 这个理论是整个java.util.concurrent包的基础。
 */
class AtomicIntPlusPlus implements Runnable {

  private AtomicInteger serialNumber = new AtomicInteger();

  @Override
  public void run() {

    try {
      Thread.sleep(200);
    } catch (InterruptedException e) {
    }

    System.out.println(getSerialNumber());
  }

  public int getSerialNumber() {
    return serialNumber.getAndIncrement();
  }

}

/**
 * 模拟 CAS 算法
 * <p>
 * synchronized 是 JVM 里东西， CAS 是硬件里的东西，有处理器负责完成。
 */
class SimulationCompareAndSwap {
  private int value;

  //获取内存值
  public synchronized int get() {
    return value;
  }

  //比较
  public synchronized int compareAndSwap(int expectedValue, int newValue) {
    int oldValue = value;

    if (oldValue == expectedValue) {
      this.value = newValue;
    }

    return oldValue;
  }

  //设置
  public synchronized boolean compareAndSet(int expectedValue, int newValue) {
    return expectedValue == compareAndSwap(expectedValue, newValue);
  }
}

/**
 * 一、 Atomic 的四种基础类型：
 * 类 AtomicBoolean、AtomicInteger、AtomicLong 和 AtomicReference 的实例各自提供对相应类型单个变量的访问和更新。
 * 他们 value 成员都是 volatile ，每个类也为该类型提供适当的实用工具方法。
 * <p>
 * 二、 Atomic 四种基础类型对应的数组类型
 * AtomicIntegerArray、AtomicLongArray 和 AtomicReferenceArray 类进一步扩展了原子操作，对这些类型的数组提供了支持。
 * 这些类在为其数组元素提供 volatile 访问语义方面也引人注目，这对于普通数组来说是不受支持的。
 * 他们内部并不是像AtomicInteger一样维持一个 volatile 变量，而是全部由native方法实现。
 * <p>
 * 三、核心方法：
 * boolean compareAndSet(expectedValue, updateValue)
 * volatile
 */

/**
 * Atomic 包中类的说明
 * 标量类（Scalar）：AtomicBoolean，AtomicInteger，AtomicLong，AtomicReference
 * 数组类：AtomicIntegerArray，AtomicLongArray，AtomicReferenceArray
 * 更新器类：AtomicLongFieldUpdater，AtomicIntegerFieldUpdater，AtomicReferenceFieldUpdater
 * 复合变量类：AtomicMarkableReference，AtomicStampedReference
 * <p>
 * AtomicStampedReference 它不是 AtomicReference 的子类，而是利用 AtomicReference 实现的一个储存引用和 Integer 组的扩展类
 */

class MultiAtomicOption implements Runnable {

  private static AtomicInteger i = new AtomicInteger(0);

  private static void add10() {

    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    i.addAndGet(1);
    i.addAndGet(2);
    i.addAndGet(3);
    i.addAndGet(4); // + 10
  }

  @Override
  public void run() {
    add10();
    System.out.println(i);
  }

}

class UseAtomicIntegerArray {

  private int[] values = new int[]{1, 2, 3};
  private AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(values);

  void test() {
    atomicIntegerArray.getAndSet(0, 9);
    System.out.println("AtomicIntegerArray 中下标为0的值" + atomicIntegerArray.get(0));
    System.out.println("int array 中下标为0的值" + values[0]);

  }

}


class UserAtomicReference {
  class User {
    private String name;
    private int age;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public int getAge() {
      return age;
    }

    public void setAge(int age) {
      this.age = age;
    }
  }

  void test() {
    User oldUser = new User();
    oldUser.setName("xxx");
    oldUser.setAge(18);
    User newUser = new User();
    newUser.setName("xxx");
    newUser.setAge(20);

    AtomicReference<User> ar = new AtomicReference<>(oldUser);
    // 更新过程是原子操作
    ar.compareAndSet(oldUser, newUser);
    System.out.println(ar.get().age);
  }
}

/**
 * 带版本戳的 AtomicReference ， 可以解决ABA问题
 */
class UseAtomicStampedReference {

  void test() {
    AtomicStampedReference<String> asr = new AtomicStampedReference<>("xxx", 0);
    final String oldRef = asr.getReference();
    final int oldStamp = asr.getStamp();
    System.out.println("oldRef = " + oldRef + ", oldStamp = " + oldStamp);
  }
}