package me.xhy.java.lang.java8.nc2Stream;

import me.xhy.java.lang.materials.dish.Dish;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static me.xhy.java.lang.materials.dish.Dish.menu;

/**
 * Created by xuhuaiyu on 2017/2/12.
 * <p>
 * 数值流
 */
public class C8NumericStreams {

  public static void main(String... args) {
    // 1. 原始类型流
    List<Integer> numbers = Arrays.asList(3, 4, 5, 1, 2);

    Arrays.stream(numbers.toArray()).forEach(System.out::println);
    int calories = menu.stream()
        .mapToInt(Dish::getCalories)
        .sum();
    System.out.println("Number of calories:" + calories);


    // max and OptionalInt
    OptionalInt maxCalories = menu.stream()
        .mapToInt(Dish::getCalories)
        .max();

    int max;
    if (maxCalories.isPresent()) {
      max = maxCalories.getAsInt();
    } else {
      // we can choose a default value
      max = 1;
    }
    System.out.println(max);

    // 2. 数值范围
    // range 和 rangeClosed 的区别在于是否接受结束值
    IntStream evenNumbers = IntStream.rangeClosed(1, 100)
        .filter(n -> n % 2 == 0);

    System.out.println(evenNumbers.count());

    Stream<int[]> pythagoreanTriples =
        IntStream.rangeClosed(1, 100).boxed() // 装箱 int -> Integer
            .flatMap(a -> IntStream.rangeClosed(a, 100)
                .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0).boxed()
                .map(b -> new int[]{a, b, (int) Math.sqrt(a * a + b * b)}));

    pythagoreanTriples.forEach(t -> System.out.println(t[0] + ", " + t[1] + ", " + t[2]));

  }

  public static boolean isPerfectSquare(int n) {
    return Math.sqrt(n) % 1 == 0;
  }

}
