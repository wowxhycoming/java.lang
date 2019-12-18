package me.xhy.java.lang.concurrent.s1base;

/**
 * yield() 并不是让出执行权，而是放弃执行权，重新与等待的线程重新争夺执行权
 * 有可能刚 yield 后，还是他执行
 */
public class S7UseYield {
  public static void main(String[] args) {
    UseYield t1 = new UseYield("t1");
    UseYield t2 = new UseYield("t2");
    t1.start();
    t2.start();
  }
}

class UseYield extends Thread {
  UseYield(String s) {
    super(s);
  }

  @Override
  public void run() {
    for (int i = 0; i <= 30; i++) {
      System.out.println(getName() + ":" + i);
      if (("t1").equals(getName())) {
        if (i == 0) {
          yield();
        }
      }
    }
  }
}
