package me.xhy.java.lang.java8.nc1Lambda;

import me.xhy.java.lang.materials.fruit.Apple;
import me.xhy.java.lang.materials.fruit.Fruit;
import me.xhy.java.lang.materials.fruit.Orange;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by xuhuaiyu on 2017/2/11.
 * <p>
 * 方法引用
 */
public class C8FunctionRefenrence {

    public static void main(String[] args) {
        Supplier<Apple> c1 = Apple::new;
        Apple a1 = c1.get();
        Supplier<Apple> c2 = () -> new Apple();
        Apple a2 = c2.get();

        Function<Integer, Apple> f1 = Apple::new;
        Apple a3 = f1.apply(100);
        Function<Integer, Apple> f2 = weight -> new Apple(weight);
        Apple a4 = f2.apply(100);

        BiFunction<Integer, String, Apple> bf1 = Apple::new;
        Apple a5 = bf1.apply(110, "green");
        BiFunction<Integer, String, Apple> bf2 = (weight, color) -> new Apple(weight, color);
        Apple a6 = bf2.apply(110, "green");

        // 获取不同水果
        // 1. 首先创建一个map ， 有水果的名字和创建水果的方式 ： Map<String, Function<Integer, Fruit>>
        // 2. 一个执行行为的方法
        // 3. 应用方法
        Apple apple = (Apple) giveMeFruit("apple", 200);

    }

    static Map<String, Function<Integer, Fruit>> map = new HashMap<>();

    public static Fruit giveMeFruit(String fruitName, Integer weight) {
        return map.get(fruitName.toLowerCase()).apply(weight);
    }

}
