package me.xhy.java.lang.java8.ncXConcurrent;

import me.xhy.java.lang.materials.data.Data;

import java.util.*;
import java.util.concurrent.*;

import static java.util.stream.Collectors.toList;

/**
 * Created by xuhuaiyu on 2017/4/13.
 * <p>
 * since 1.8
 * <p>
 * 模拟场景：
 * 通过银行接口查询给定名称的贵金属单价。
 * <p>
 * 思路：
 * 一、 先实现同步 API
 * 1. 设计一个银行的实体 Bank 。
 * 2. Bank 提供按指定金属名称返回价格的方法 getPrice() 。
 * 3. Bank 提供模拟查询股票价格的算法 calculate() ； 被 getPrice() 调用。
 * 4. 设计一个延迟类 Delay，提供用于表示操作耗时的静态方法 delay() ； 在 calculate() 中调用。
 */
public class C2CompletableFuture {

  static int availableProcessors = 0;
  static int count = 0;
  static int fjpSize = 0;

  static {
    availableProcessors = Runtime.getRuntime().availableProcessors();
    count = availableProcessors;
    fjpSize = ForkJoinPool.commonPool().getPoolSize();
  }

//    private static ExecutorService pool = Executors.newFixedThreadPool(100, r -> {
//        Thread t = new Thread(r);
//        t.setDaemon(true);
//        return t;
//    });

  public static void main(String[] args) {

    // 询价的金属名称
    String metalName = "iron";
    System.out.println("阻塞任务数为 cpu 核心数 ： " + count + " 。 程序中使用变量 count ");

    // 同步API
//    useSyncAPI(metalName);

    // 并行的同步API
//    useParallelSyncAPI(metalName);

    // 异步API
//    useASyncAPI(metalName);

    // 异步API，没有处理异常
//        useASyncAPIHasExWithOutCT(metalName);

    // 异步API，内部处理异常
//        useASyncAPIHasEx(metalName);

    // 异步API，使用 CompletableFuture 提供的静态方法 ，变同步方法为异步
//        useFactory();

    // 在指定银行查询金属价格 同步方法
//        queryMetalPriceSync(metalName);

    // 在指定银行查询金属价格 异步方法
//        queryMetalPriceAsync(metalName);

    // 比较 同步并行 和 异步
//        compareParallelAndAsync(metalName, count+10);

    // 使用自定义执行器
        queryMetalPriceAsyncIndividuationExecutor(metalName);

    // 对多个异步任务操作
//        queryMetalPriceAndDiscountAsycn(metalName);

    // 多个独立异步任务合并
//        queryMetalAndRate(metalName, Money.Code.EUR);


    // before

    // 查询贵金属价格
//        long start = System.nanoTime();
//        List<Double> list = queryMetalPrice(metalName);
//        list.forEach(System.out::println);
//        System.out.println("queryMetalPrice spend : " + (System.nanoTime() - start) / 1_000_000 + " ms");

    // 带格式的返回结果
//        queryMetalPriceFormat("iron").forEach(System.out::println);

    // 带折扣信息的结果 , 根据异步数量，使用合理的线程池
//        queryPriceAndDiscountAsync("iron").forEach(System.out::println);

    // 异步完成后自动处理 和 多个异步任务协作
//        findPrice("iron");


  }

  private static void useSyncAPI(String metalName) {
    long start = System.nanoTime();

    Bank.getBanks(count).stream()
        .map(bank -> String.format("%7s : %.2f", bank.getBankName(), bank.getPrice(metalName)))
        .forEach(System.out::println);

    System.out.println("useSyncAPI spend : " + (System.nanoTime() - start) / 1_000_000 + " ms");
    System.out.println("串行版本的时间自然是所有任务阻塞时间的和");
  }

  private static void useParallelSyncAPI(String metalName) {
    long start = System.nanoTime();

    Bank.getBanks(count).parallelStream()
        .map(bank -> String.format("%7s : %.2f", bank.getBankName(), bank.getPrice(metalName)))
        .forEach(System.out::println);

    System.out.println("useParallelSyncAPI spend : " + (System.nanoTime() - start) / 1_000_000 + " ms");
  }

  private static void useASyncAPI(String metalName) {
    long start = System.nanoTime();

    List<CompletableFuture<Double>> futures =
        Bank.getBanks(count).stream()
            .map(bank -> bank.getPriceAsync(metalName))
            .collect(toList());

    System.out.println("useASyncAPI spend : " + (System.nanoTime() - start) / 1_000_000 + " ms");

    // 大量计算
    Bank.somethingElse();

    futures.forEach(i -> {
      try {
        System.out.println(i.get());
      } catch (Exception e) {
        e.printStackTrace();
      }
    });

    System.out.println("result return spend " + (System.nanoTime() - start) / 1_000_000 + " ms");

  }

  private static void useASyncAPIHasExWithOutCT(String metalName) {
    long start = System.nanoTime();

    List<CompletableFuture<Double>> futures =
        Bank.getBanks(1).stream()
            .map(bank -> bank.getPriceAsyncHasExWhitOutTC(metalName))
            .collect(toList());

    System.out.println("useASyncAPIHasEx spend : " + (System.nanoTime() - start) / 1_000_000 + " ms");

    // 大量计算
    Bank.somethingElse();

    futures.forEach(i -> {
      try {
        System.out.println("useASyncAPIHasExWithOutCT : 将不会捕捉到 Future 中的异常");
        System.out.println(i.get());
      } catch (Exception e) {
        System.out.println("useASyncAPIHasExWithOutCT : catch");
        e.printStackTrace();
      }
    });

    System.out.println("result return spend " + (System.nanoTime() - start) / 1_000_000 + " ms");

  }

  private static void useASyncAPIHasEx(String metalName) {
    long start = System.nanoTime();

    List<CompletableFuture<Double>> futures =
        Bank.getBanks(1).stream()
            .map(bank -> bank.getPriceAsyncHasEx(metalName))
            .collect(toList());

    System.out.println("useASyncAPIHasEx spend : " + (System.nanoTime() - start) / 1_000_000 + " ms");

    // 大量计算
    Bank.somethingElse();

    futures.forEach(i -> {
      try {
        System.out.println("===1===");
        System.out.println(i.get());
        System.out.println("===2===");
      } catch (Exception e) {
        System.out.println("useASyncAPIHasEx catch");
        e.printStackTrace();
      }
    });

    System.out.println("result return spend " + (System.nanoTime() - start) / 1_000_000 + " ms");

  }

  private static void useFactory() {
    long start = System.nanoTime();

    List<CompletableFuture<Double>> futures =
        Bank.getBanks(count).stream()
            .map(bank -> bank.getPriceAsyncUseFactory("iron"))
            .collect(toList());

    System.out.println("useFactory submit spend : " + (System.nanoTime() - start) / 1_000_000 + " ms");
    System.out.println("===========ForkJoinPool 线程池属性============");
    System.out.println(ForkJoinPool.getCommonPoolParallelism());
    System.out.println(ForkJoinPool.getCommonPoolParallelism() > 1);
    System.out.println(ForkJoinPool.commonPool().getPoolSize());
    System.out.println("===========ForkJoinPool 线程池属性============");

    futures.forEach(i -> {
      try {
        System.out.println(i.get());
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (ExecutionException e) {
        e.printStackTrace();
      }
    });

    System.out.println("useFactory finish spend : " + (System.nanoTime() - start) / 1_000_000 + " ms");

  }

  // 开始开发价格查询接口
  private static void queryMetalPriceSync(String metalName) {
    long start = System.nanoTime();

    List<Bank> banks = Bank.getBanks(2);
    Bank bank = new Bank("");
    bank.findPricesSycn(metalName, banks).forEach(System.out::println);

    System.out.println("queryMetalPriceSync finish spend : " + (System.nanoTime() - start) / 1_000_000 + " ms");
  }

  // 使用 supplyAsync 方法创建异步方法 , 最终异步方法的返回值与同步方法一样，仍然是 List<String>
  private static void queryMetalPriceAsync(String metalName) {
    long start = System.nanoTime();

    List<Bank> banks = Bank.getBanks(count);
    Bank bank = new Bank("");
    bank.findPricesAsycn(metalName, banks).forEach(System.out::println);

    System.out.println("queryMetalPrice submit spend : " + (System.nanoTime() - start) / 1_000_000 + " ms");
    System.out.println("与同步*串*行版本的方法比较，是快了");
    System.out.println("与同步*并*行版本的方法比较，消耗了 2 倍的时间");
  }

  // 比较并行任务 和 异步任务
  private static void compareParallelAndAsync(String metalName, int jobCount) {
    long start = System.nanoTime();
    int count = jobCount;
    List<Bank> banks = Bank.getBanks(count);
    System.out.println("=====================");

    banks.parallelStream()
        .map(bank -> String.format("%7s : %.2f", bank.getBankName(), bank.getPrice(metalName)))
        .forEach(System.out::println);

    System.out.println("useParallelSyncAPI spend : " + (System.nanoTime() - start) / 1_000_000 + " ms");

    System.out.println("=====================");

    start = System.nanoTime();
    Bank bank = new Bank("");
    bank.findPricesAsycn(metalName, banks).forEach(System.out::println);

    System.out.println("queryMetalPriceAsync submit spend : " + (System.nanoTime() - start) / 1_000_000 + " ms");

    System.out.println("=====================");
    System.out.println("parallel 采用的是通用线程池，线程各位为 cpu 核心数 ");
    System.out.println("CompletableFuture 比较有优势的地方是可以自定义线程池，当然也能设置线程池大小；而这是 parallel 无法提供的。");
  }

  // 使用自定义执行器
  private static void queryMetalPriceAsyncIndividuationExecutor(String metalName) {
    /**
     * 怎样合理配置线程池的大小
     * N_threads = N_cpu * U_cpu * (1 + W/C)
     * N_threads : 合理的线程数
     * N_cpu : 处理器核心数
     * U_cpu : 希望CPU的利用率（0 至 1 之间）
     * W/C : 等待时间与计算时间的比率（比率为 % 前面的数组）
     *
     * 对于查询价格 99.99% 的时间都在等待相应，所以 W/C = 100 ；
     * 如果想要 CPU 利用率为 100% 则 = 8 * 1 * (1+100) = 808 个线程
     *
     * 实际中，如果创建的线程数比银行数量还多，明显是一种浪费
     * 当然，也不能，按银行的数量设置线程数，如果数量巨大，会导致系统崩溃，需要设置线程池上限
     *
     * 为什么要创建多个线程
     * 任务的执行载体是线程，一个 cpu 可以在线程间切换，但是一个 thread 不能在多个任务间切换。
     */
    // 这里需要一个线程池，来配合出演
    Executor executor = ExecutorSupply.getExecutor(100);

    long start = System.nanoTime();

    List<Bank> banks = Bank.getBanks(1000);
    Bank bank = new Bank("");

    bank.findPricesAsycn(metalName, banks, executor).forEach(System.out::println);

    System.out.println("queryMetalPriceAsyncIndividuationExecutor submit spend : " + (System.nanoTime() - start) / 1_000_000 + " ms");

  }

  private static void queryMetalPriceAndDiscountAsycn(String metalName) {

    Executor executor = ExecutorSupply.getExecutor(count + 20);
    List<Bank> banks = Bank.getBanks(count + 20);

    long start = System.nanoTime();

    List<CompletableFuture<Quote>> quoteFuture =
        banks.stream()
            .map(bank -> {
              Quote quote = new Quote();
              quote.setBankName(bank.getBankName());
              quote.setMetalName(metalName);
              return quote;
            }).map(quote -> CompletableFuture.supplyAsync(
            () -> quote.getPriceQuote(quote), executor
        ))//.map(future -> future.thenApply(quote -> quote))
            .map(future -> future.thenCompose(quote -> CompletableFuture.supplyAsync(
                () -> quote.getDiscountQuote(quote), executor
            )))//.map(future -> future.thenApply(quote -> quote))
            .collect(toList());

    List<Quote> result =
        quoteFuture.stream().map(CompletableFuture::join)
            .collect(toList());

    System.out.println(result);
    System.out.println("queryMetalPriceAndDiscountAsycn spend : " + (System.nanoTime() - start) / 1_000_000 + " ms");
    /**
     * thenCompose 提供了异步方法，thenComposeAsync 与 thenCompose 相比，执行线程不再相同。
     */

  }

  /**
   * 将多个独立的异步任务合并
   * 在银行查询完金属价格，如果要使用外币交易的时候，需要货币汇率转换
   * 这些银行默认本币为 RMB ， 对外币币种进行汇率转换 。 Money 类是一个枚举，提供货币类型
   *
   * queryRate 不依赖 getPrice 的结果，两个独立的任务，在最后一一对应
   */
  private static void queryMetalAndRate(String metal, Money.Code code) {

    Executor executor = ExecutorSupply.getExecutor(count + 20);
    List<Bank> banks = Bank.getBanks(count + 20);

    long start = System.nanoTime();

    List<CompletableFuture<Double>> collect = banks.stream()
        .map(b -> CompletableFuture.supplyAsync(() -> b.getPrice(metal), executor)
            .thenCombine(
                CompletableFuture.supplyAsync(() -> Money.queryRate(code), executor),
                (price, rate) -> price * rate
            )
        ).collect(toList());

    List<Double> result = collect.stream().map(CompletableFuture::join).collect(toList());

    System.out.println(result);

    System.out.println("queryMetalPriceAndDiscountAsycn spend : " + (System.nanoTime() - start) / 1_000_000 + " ms");

  }

}

class Delay {

  public static void delay() {
    try {
      Thread.sleep(2 * 1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}

class ExecutorSupply {

  public static ExecutorService getExecutor(int capacity) {
    return Executors.newFixedThreadPool(
        capacity,
        r -> {
          Thread t = new Thread(r);
          t.setDaemon(true); // 设置成守护线程：这种方式不会影响程序关停
          return t;
        });
  }

}

// 银行
class Bank {

  private static List<Bank> bankList;

  // 任务的数量 和 CPU线程数 会影响执行效率
  static {
    bankList = new ArrayList<>();
    bankList.add(new Bank("BOC"));
    bankList.add(new Bank("ABC"));
    bankList.add(new Bank("CCB"));
    bankList.add(new Bank("ICBC"));
    for (int i = 0; i < 1000; i++) {
      bankList.add(new Bank(String.valueOf(i)));
    }
  }

  private Random random = new Random();

  private String bankName;

  public Bank(String bankName) {
    this.bankName = bankName;
  }

  public String getBankName() {
    return bankName;
  }

  public void setBankName(String bankName) {
    this.bankName = bankName;
  }

  public static List<Bank> getBanks(int count) {
    int size = bankList.size();
    return bankList.subList(0, count > size ? size : count);
  }

  private boolean returnTrue() {
    return true;
  }

  // 计算价格的方法，使用金属名称 和 charAt 计算返回一个数值
  public double calculatePrice(String name) {
    System.out.println("delay");

    Delay.delay();
//        try {
//            TimeUnit.SECONDS.sleep(new Random().nextInt(3));
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

    if (null == name || name.length() < 2) {
      return 0;
    }

    return random.nextDouble() * name.charAt(0) + name.charAt(1);
  }

  public double getPrice(String metalName) {

    return calculatePrice(metalName);
  }

  public CompletableFuture<Double> getPriceAsync(String metalName) {

    CompletableFuture<Double> futurePrice = new CompletableFuture<>();
    new Thread(() -> {
      // 阻塞的方法一定要放在线程中
      double price = calculatePrice(metalName);
      // 内部异常，将导致 client 一直等待下去， client 应该使用带有超时参数的重载的 get() 方法
//            price = 1/0;
      futurePrice.complete(price);
    }).start();

    return futurePrice;
  }

  public static void somethingElse() {
    System.out.println("这是一个或多个特别耗费CPU的计算");
  }

  private double calculatePriceHasEx(String name) {

    Delay.delay();

    if (null == name || name.length() < 2) {
      return 0;
    }

    if (this.returnTrue()) throw new RuntimeException("calculate price failed");

    return random.nextDouble() * name.charAt(0) + name.charAt(1);
  }

  public CompletableFuture<Double> getPriceAsyncHasExWhitOutTC(String metalName) {

    CompletableFuture<Double> futurePrice = new CompletableFuture<>();
    new Thread(() -> {
      System.out.println("getPriceAsyncHasExWhitOutTC : 异步任务将抛出异常，异常会被限制在当前线程中，最终杀死当前线程，导致调用该方法的客户端永远的阻塞下去，程序不会自动退出");
      double price = calculatePriceHasEx(metalName);
      futurePrice.complete(price);
    }).start();

    return futurePrice;
  }

  public CompletableFuture<Double> getPriceAsyncHasEx(String metalName) {

    CompletableFuture<Double> futurePrice = new CompletableFuture<>();
    new Thread(() -> {
      try {
        System.out.println("getPriceAsyncHasEx try ： 要在异步方法的线程中处理异常，最好 try 整个逻辑");
        double price = calculatePriceHasEx(metalName);
        futurePrice.complete(price);
      } catch (Exception e) {
        System.out.println("getPriceAsyncHasEx catch");
        futurePrice.completeExceptionally(e);
      }
    }).start();

    return futurePrice;
  }

  public CompletableFuture<Double> getPriceAsyncUseFactory(String metalName) {

    // CompletableFuture.supplyAsync 会返回 CompletableFuture 对象，传入的 Supplier 只需要关心要处理的数据即可， lambda 的环绕应用
    // CompletableFuture.supplyAsync 会使用特定数量的线程池，如果执行时间有差异，去 supplyAsync 方法中看看。
    // 使用 CompletableFuture.supplyAsync 为我们考虑了错误处理， 不用花费更多的经历去处理异常了
    // supplyAsync 默认使用ForkAndJoinPool线程池；也提供了重载方法，可以自定义线程池
    return CompletableFuture.supplyAsync(() -> calculatePrice(metalName));

  }

  // 查询价格 同步方法
  public List<String> findPricesSycn(String metalName, List<Bank> banks) {
    return
        banks.stream()
            .map(bank -> String.format("%7s price is %.2f", bank.getBankName(), bank.getPrice(metalName)))
            .collect(toList());
  }

  // 查询价格中间方法 封装同步方法 变为异步方法 （不是最终要提供的方法，最终的返回值类型应该是 List<String>）
  public List<CompletableFuture<String>> findPricesAsycnIsNotWeWant(String metalName, List<Bank> banks) {
    return
        banks.stream()
            .map(bank -> CompletableFuture.supplyAsync(
                () -> String.format("%7s price is %.2f", bank.getBankName(), bank.getPrice(metalName))
                )
            ).collect(toList());
  }

  // 查询价格 异步方法
  public List<String> findPricesAsycn(String metalName, List<Bank> banks) {
    List<CompletableFuture<String>> resultFutures =
        banks.stream()
            .map(bank ->
                CompletableFuture.supplyAsync(
                    () -> String.format("%7s price is %.2f", bank.getBankName(), bank.getPrice(metalName))
                )
            ).collect(toList());

    return
        resultFutures.stream()
            .map(CompletableFuture::join)
            .collect(toList());

    /**
     * 为什么这里要使用两次 stream
     * 1. stream 是顺序操作，
     *  在每一个元素 map 成 CompletableFuture 后，
     *  如果马上 map 成结果String 的话：e.get() ，就会发生阻塞
     * 2. 因为要异步的查询，所以不能把阻塞串联起来。
     *  那么 CompletableFuture 是可以快速返回的， 所以第一个收集到 CompletableFuture 这一层即可。
     *  拿到一个未来结果许可的集合。
     * 3. 接下来对 List<CompletableFuture<String>> 的流进行操作，分别取结果。
     */
  }

  // 查询价格 带线程池的异步方法
  public List<String> findPricesAsycn(String metalName, List<Bank> banks, Executor executor) {
    if (executor == null) {
      this.findPricesAsycn(metalName, banks);
    }

    List<CompletableFuture<String>> resultFutures =
        banks.stream()
            .map(bank ->
                CompletableFuture.supplyAsync(
                    () -> String.format("%7s price is %.2f", bank.getBankName(), bank.getPrice(metalName)),
                    executor
                )
            ).collect(toList());

    return
        resultFutures.stream()
            .map(CompletableFuture::join)
            .collect(toList());
  }

  // 多个异步操作
  // 查询价格返回报价对象-用报价对象查询折扣、计算最终价格，返回报价对象
  // 1. 查询价格 基础方法
  public Quote getPriceQuote(Quote quote) {

    // 阻塞操作
    quote.setBankName(bankName);
    quote.setPrice(calculatePrice(quote.getMetalName()));

    return quote;
  }


  //2. 查询折扣
  public Quote getDiscountQuote(Quote quote) {

    // 阻塞操作
    Discount.Code code = new Discount().applyDiscount(quote);
    quote.setDiscountCode(code);

    return quote;
  }

  // 3. 计算最终价格
  public void getPriceFinal(Quote quote) {


  }


}

// 折扣

/**
 * 所有银行共用一套会员体系，这个体系提供购买时可享用的折扣价格
 * 折扣信息应该按用户给出，这里没有用户，就随机给出了：每次来获取折扣信息都可能得到不同的结果
 */
class Discount {

  public enum Code {

    NONE(0), SILVER(5), GOLD(10), PLATINUM(15), DIAMOND(20);

    private int discount;

    Code(int discount) {
      this.discount = discount;
    }
  }

  public Discount.Code applyDiscount(Quote quote) {

    Delay.delay();

    Random random = new Random();
    Discount.Code code = Discount.Code.values()[random.nextInt(Code.values().length)];

    return code;
  }

}

// 报价
class Quote {

  private String bankName;
  private String metalName;
  private double price;
  private Discount.Code discountCode;
  private String finalPrice;

  @Override
  public String toString() {
    return "Quote{" +
        "bankName='" + bankName + '\'' +
        ", metalName='" + metalName + '\'' +
        ", price=" + price +
        ", discountCode=" + discountCode +
        ", finalPrice='" + finalPrice + '\'' +
        '}';
  }

  public String getBankName() {
    return bankName;
  }

  public void setBankName(String bankName) {
    this.bankName = bankName;
  }

  public String getMetalName() {
    return metalName;
  }

  public void setMetalName(String metalName) {
    this.metalName = metalName;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public Discount.Code getDiscountCode() {
    return discountCode;
  }

  public void setDiscountCode(Discount.Code discountCode) {
    this.discountCode = discountCode;
  }

  public String getFinalPrice() {
    return finalPrice;
  }

  public void setFinalPrice(String finalPrice) {
    this.finalPrice = finalPrice;
  }

  // 多个异步操作
  // 查询价格返回报价对象-用报价对象查询折扣、计算最终价格，返回报价对象
  // 1. 查询价格 基础方法
  public Quote getPriceQuote(Quote quote) {

    quote.setBankName(bankName);
    // 阻塞操作
    quote.setPrice(new Bank(bankName).calculatePrice(quote.getMetalName()));

    return quote;
  }


  //2. 查询折扣
  public Quote getDiscountQuote(Quote quote) {

    // 阻塞操作
    Discount.Code code = new Discount().applyDiscount(quote);
    quote.setDiscountCode(code);

    return quote;
  }

  // 3. 计算最终价格
  public void getPriceFinal(Quote quote) {

    Delay.delay();


  }
}

class Money {

  public enum Code {
    CNY(1), UED(0.1453), EUR(0.1297);

    private double rate;

    Code(double rate) {
      this.rate = rate;
    }
  }

  public static double queryRate(Money.Code code) {

    Delay.delay();

    Optional<Code> optional = Arrays.stream(Code.values()).reduce((i, j) -> i == code ? i : j);

    return optional.isPresent() ? optional.get().rate : 1;
  }


}