package me.xhy.java.lang.java8.nc6DateTime;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author xxx
 * @since 2022-04-20 16:18
 */

public class C1OldQuestion {
  public static void main(String[] args) {
    // SimpleDateFormat 格式化日期的线程安全问题。执行过程中会报异常，已经无法解析了，格式出了问题
    // 例：java.util.concurrent.ExecutionException: java.lang.NumberFormatException: For input string: ".22001299902119901920192019E4E4E4E4"

    threadNotSafe();

    // 解决 SimpleDateFormat 的线程不安全问题
    // 1. 将 SimpleDateFormat 定义为局部变量
    // 2. 使用 synchronized 加锁执行
    // 3. 使用 Lock 加锁执行（和解决方案 2 类似）
    // 4. 使用 ThreadLocal
    // 5. 使用 JDK 8 中提供的 DateTimeFormat

    // 新的日期时间API
    threadSafeNewApi();

  }


  static void threadNotSafe() {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
    Callable<Date> task = () -> simpleDateFormat.parse("20190203");
    ExecutorService threadPool = Executors.newFixedThreadPool(10);
    List<Future<Date>> results = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      results.add(threadPool.submit(task));
    }
    results.forEach(f -> {
      try {
        System.out.println(f.get());
      } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
      }
    });
    threadPool.shutdown();
  }

  static void threadSafeNewApi()  {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    Callable<LocalDate> task = () -> LocalDate.parse("20190203",dateTimeFormatter);
    ExecutorService threadPool = Executors.newFixedThreadPool(10);
    List<Future<LocalDate>> results = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      results.add(threadPool.submit(task));
    }
    results.forEach(f -> {
      try {
        System.out.println(f.get());
      } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
      }
    });
    threadPool.shutdown();
  }
}
