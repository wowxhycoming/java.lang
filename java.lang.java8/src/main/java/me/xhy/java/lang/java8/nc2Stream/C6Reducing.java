package me.xhy.java.lang.java8.nc2Stream;

import me.xhy.java.lang.materials.dish.Dish;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static me.xhy.java.lang.materials.dish.Dish.menu;

/**
 * Created by xuhuaiyu on 2017/2/12.
 * <p>
 * 规约
 */
public class C6Reducing {

    public static void main(String... args) {

        List<Integer> numbers = Arrays.asList(3, 4, 5, 1, 2);

        // 求和
        int sum = numbers.stream().reduce(0, (a, b) -> a + b);
        System.out.println(sum);

        int sum2 = numbers.stream().reduce(0, Integer::sum);
        System.out.println(sum2);

        // 最大值
        int max = numbers.stream().reduce(0, (a, b) -> Integer.max(a, b));
        System.out.println(max);

        // 最小值
        Optional<Integer> min = numbers.stream().reduce(Integer::min);
        min.ifPresent(System.out::println);
        System.out.println(min.get());

        /*
        Optional<T>
        ifPresent() 在有值的时候返回true，否则返回false
        ifPresent(Consumer<T> c) 会在值为true的时候执行给定代码块
        T get() 在只存在的时候返回值，否则抛出 NoSuchElement 异常
        T orElse() 会在值存在时返回值，否则返回一个默认值
         */

        int calories = menu.stream()
                .map(Dish::getCalories)
                .reduce(0, Integer::sum);
        System.out.println("Number of calories:" + calories);

        // 使用并行流进行规约
        int calories2 = menu.parallelStream()
                .map(Dish::getCalories)
                .reduce(0, Integer::sum);
        System.out.println("Number of calories:" + calories2);
    }
}
