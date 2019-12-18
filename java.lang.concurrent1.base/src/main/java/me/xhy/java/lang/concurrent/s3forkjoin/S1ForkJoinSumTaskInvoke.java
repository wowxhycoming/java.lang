package me.xhy.java.lang.concurrent.s3forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class S1ForkJoinSumTaskInvoke {
  public static void main(String[] args) {
    long start = System.currentTimeMillis();
    int capacity = Integer.MAX_VALUE >> 3;
    System.out.println("capacity:" + capacity);
    long[] src = new long[capacity];
    for (int i = 0; i < capacity; i++) {
      src[i] = i;
    }

    ForkJoinPool pool = new ForkJoinPool();
    Long l = pool.invoke(new SumTask(src, 0, src.length - 1));
    long end = System.currentTimeMillis();
    System.out.println("耗时: " + (end - start));
    System.out.println("结果: " + l);

  }
}

class SumTask extends RecursiveTask<Long> {

  private long[] src;
  private long fromIndex;
  private long toIndex;

  SumTask(long[] src, long fromIndex, long toIndex) {
    this.src = src;
    this.fromIndex = fromIndex;
    this.toIndex = toIndex;
  }

  @Override
  protected Long compute() {

    if (toIndex - fromIndex > 10000) {
      long middle = (fromIndex + toIndex) / 2;
      SumTask left = new SumTask(src, fromIndex, middle);
      SumTask right = new SumTask(src, middle + 1, toIndex);
      // fork 分解任务
      // invokeAll(left, right); 等价 left.fork() + right.fork()
      left.fork();
      right.fork();
      // join 收集任务结果， 然后处理
      return left.join() + right.join();
    } else {
      long res = 0;
      for (int i = Integer.parseInt(String.valueOf(fromIndex)); i < toIndex; i++) {
        res += src[i];
      }
      return res;
    }

  }
}
