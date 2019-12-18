package me.xhy.java.lang.concurrent.s1base;

/**
 * 不能锁 基础数据类型， 因为基础数据类型每次运算后，内存地址都会发生变化
 * i++ 实际调用的是 Integer 的方法，最后返回了一个 new Integer 对象
 */
public class S9IntegerSynchronized {

  public static void main(String[] args) throws InterruptedException {
    Worker worker = new Worker(1);
    //Thread.sleep(50);
    for (int i = 0; i < 5; i++) {
      new Thread(worker).start();
    }
  }

  private static class Worker implements Runnable {

    private Integer i;
    private Object o = new Object();

    public Worker(Integer i) {
      this.i = i;
    }

    @Override
    public void run() {
      synchronized (o) {
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + "           : i = " + i + ",identityHashCode = "
            + System.identityHashCode(i));
        i++;
        System.out.println(thread.getName() + " after i++ : i = " + i + ",identityHashCode = "
            + System.identityHashCode(i));
        try {
          Thread.sleep(3000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }

    }

  }

}
