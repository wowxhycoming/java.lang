package me.xhy.java.lang.concurrent.s1base;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * java 天生就是多线程的
 * 运行一个 main 线程个，jvm 中会产生多少个线程
 */
public class S1OnlyMain {
  public static void main(String[] args) {
    //Java 虚拟机线程系统的管理接口
    ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
    // 不需要获取同步的monitor和synchronizer信息，仅仅获取线程和线程堆栈信息
    ThreadInfo[] threadInfos =
        threadMXBean.dumpAllThreads(false, false);
    // 遍历线程信息，仅打印线程ID和线程名称信息
    for (ThreadInfo threadInfo : threadInfos) {
      System.out.println("[" + threadInfo.getThreadId() + "] "
          + threadInfo.getThreadName());
    }
  }
}

/**
 * output :nm bmb
 * [6] Monitor Ctrl-Break
 * [5] Attach Listener
 * [4] Signal Dispatcher
 * [3] Finalizer
 * [2] Reference Handler
 * [1] main
 */