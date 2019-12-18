package me.xhy.java.lang.concurrent.s3forkjoin;

import java.io.File;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

public class S2ForkJoinActionExecute {
  public static void main(String[] args) throws InterruptedException {
    ForkJoinPool pool = new ForkJoinPool();
    File dir = new File("/");
    System.out.println(dir.getAbsolutePath());

    DirRecursiveWorker task = new DirRecursiveWorker(dir);

    // 异步提交
    pool.execute(task);

    // 主线程可以做自己的事情
    System.out.println(Thread.currentThread().getName() + ": working after executed task");

    // 阻塞，否则主线程结束，程序就结束了
    pool.awaitTermination(2, TimeUnit.SECONDS);
  }

}

class DirRecursiveWorker extends RecursiveAction {

  private File file;

  DirRecursiveWorker(File file) {
    this.file = file;
  }

  @Override
  protected void compute() {

    File[] files = file.listFiles();

    for (File f : files != null ? files : new File[0]) {
      System.out.println(f.getAbsolutePath());
      if (f.isDirectory()) {
        ForkJoinTask t = new DirRecursiveWorker(f);
        t.fork();
      } else {
        System.out.println(f.getAbsoluteFile());
      }
    }
  }
}