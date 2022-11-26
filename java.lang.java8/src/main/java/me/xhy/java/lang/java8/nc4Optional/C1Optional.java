package me.xhy.java.lang.java8.nc4Optional;

import me.xhy.java.lang.materials.trade.TradeData;
import me.xhy.java.lang.materials.trade.Trader;
import me.xhy.java.lang.materials.trade.Transaction;

import java.nio.charset.StandardCharsets;
import java.sql.SQLOutput;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author xxx
 * @since 2022-04-19 16:08
 */
public class C1Optional {

  /*
  Optional 类中的常用方法

  1. empty(),of(T value),ofNullable(T value)
  of(T value) 用给定值创建一个 Optional 对象，如果给定值为 null， 会报 NullPointException
  empty() 创建一个固定的 Optional 对象， 在 stream 中有特别的用处 private static final Optional<?> EMPTY = new Optional<>();
  ofNullable(T value) 给定值创建 Optional ，加上了判断给定值为空的判断 return value == null ? empty() : of(value);

  ofNullable 相比 of 在给定值为 null 时不会报错，并给出默认值

  2. ofElse(T t)
  如果包含之就返回那个值；如果不包含值就返回 t

  3. orElseGet(Supplier s)
  如果包含之就返回那个值；如果不包含值就返回 s 获取的值

  4. map(Function f)
  如果有值，进行 map 操作并返回 Optional ； 如果没值，返回 Optional.empty()

  5. flatMap(Function f)
   */

  public static void main(String[] args) throws Exception {
    System.out.println(getCity(TradeData.transactions.get(0)));
    System.out.println(getCityJava8(TradeData.transactions.get(0)));
  }

  // before java 8
  public static String getCity(Transaction txn) throws Exception {
    if (txn != null) {
      if (txn.getTrader() != null) {
        Trader trader = txn.getTrader();
        if (trader.getCity() != null) {
          return trader.getCity();
        }
      }
    }
    throw new Exception("取值错误");
  }

  // java 8
  public static Object getCityJava8(Transaction txn) throws Exception {
    return Optional.ofNullable(txn)
        .map(Transaction::getTrader)
        .map(Trader::getCity)
        .orElseThrow(()->new Exception("取指错误"));
  }
}
