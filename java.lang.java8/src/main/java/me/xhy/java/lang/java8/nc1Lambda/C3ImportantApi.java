package me.xhy.java.lang.java8.nc1Lambda;

import me.xhy.java.lang.materials.fruit.Apple;

import java.util.ArrayList;
import java.util.List;
import java.util.function.*;

/**
 * Created by xuhuaiyu on 2017/2/7.
 *
 * 常用的API
 */
public class C3ImportantApi {
    /**
     * Predicate<T> T boolean
     * Consumer<T> T void
     * Function<T, R> T R
     * Supplier<T> None T
     * UnaryOperator<T> T T
     * BinaryOperator<T,T> (T,T) T
     */

    public static void main(String[] args) {

        List<Apple> inventory = Apple.getSomeApples();

        // 利用 Predicate 筛选绿色的苹果
        List<Apple> greenApples = filterApple(inventory, apple -> "green".equals(apple.getColor()));
        List<Apple> heavyApples = filterApple(inventory, apple -> apple.getWeight() > 150);


    }

    // 通过参数传递行为
    public static List<Apple> filterApple(List<Apple> inventory, Predicate<Apple> p) {
        List<Apple> result = new ArrayList<>();

        for (Apple apple : inventory) {
            if (p.test(apple)) {
                result.add(apple);
            }
        }

        return result;
    }

}
