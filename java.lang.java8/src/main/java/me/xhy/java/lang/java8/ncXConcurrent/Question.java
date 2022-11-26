package me.xhy.java.lang.java8.ncXConcurrent;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Question {
  public static void main(String[] args) {

    Executor executor = Executors.newFixedThreadPool(100);

    List<CompletableFuture<String>> resultFutures =
        Stream.iterate(0, n -> n + 1)
            .limit(1000) // 任务数量
            .map(i -> CompletableFuture.supplyAsync(
                () -> delay() + i, // 阻塞任务
                executor
            )).collect(toList());

    List<String> collect = resultFutures.stream()
        .map(CompletableFuture::join)
        .collect(toList());

    collect.forEach(System.out::println);
  }

  static String delay() {
    System.out.println("delay");
    try {
      TimeUnit.SECONDS.sleep(3600); // 阻塞一小时
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return "=======";
  }
}
