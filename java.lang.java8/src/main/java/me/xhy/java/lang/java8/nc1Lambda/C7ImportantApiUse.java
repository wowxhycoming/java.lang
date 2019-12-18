package me.xhy.java.lang.java8.nc1Lambda;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.Arrays.asList;

/**
 * Created by xuhuaiyu on 2017/2/11.
 */
public class C7ImportantApiUse {

  // Predicate

  /**
   * @FunctionalInterface public interface Predicate<T> {
   * boolean test(T t);
   * }
   */

  public static <T> List<T> filter(List<T> list, Predicate<T> p) {
    List<T> results = new ArrayList<T>();
    for (T t : list) {
      if (p.test(t)) {
        results.add(t);
      }
    }
    return results;
  }

  static Predicate<String> nonEmptyStringPredicate = (String s) -> !s.isEmpty();

  public static List<String> testPredicate() {
    List<String> nonEmpty = filter(asList("1", "", "2"), nonEmptyStringPredicate);
    return nonEmpty;
  }


  // Consumer

  /**
   * @FunctionalInterface public interface Consumer<T> {
   * void accept(T t);
   * }
   */

  public static <T> void forEachList(List<T> list, Consumer<T> c) {
    for (T t : list) {
      c.accept(t);
    }
  }

  public static void testConsumer() {
    forEachList(asList(1, 2, 3, 4, 5), i -> System.out.println(i));
  }

  // Function

  /**
   * @FunctionalInterface public interface Function<T,R> {
   * R apply(T t);
   * }
   */

  public static <T, R> List<R> map(List<T> list, Function<T, R> f) {
    List<R> results = new ArrayList<>();
    for (T t : list) {
      R r = f.apply(t);
      results.add(r);
    }
    return results;
  }

  public static List<Integer> testFunction() {
    List<Integer> list = map(asList("1", "22", "333"), s -> s.length());
    return list;
  }


}
