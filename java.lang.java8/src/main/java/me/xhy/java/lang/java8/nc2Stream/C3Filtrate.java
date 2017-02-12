package me.xhy.java.lang.java8.nc2Stream;

import me.xhy.java.lang.materials.dish.Dish;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by xuhuaiyu on 2017/2/12.
 * <p>
 * 筛选
 */
public class C3Filtrate {

    public static List<Dish> menu = Dish.menu;

    public static void main(String[] args) {
        // 1. 用谓词筛选
        List<Dish> vegetarianMenu = menu.stream().
                filter(Dish::isVegetarian) // 谓词过滤
                .collect(toList());

        vegetarianMenu.forEach(System.out::println);

        // 2. 各异的元素
        List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
        numbers.stream()
                .filter(i -> i % 2 == 0)
                .distinct() // 去重
                .forEach(System.out::println);

        // 3. 截短
        List<Dish> dishesLimit3 = menu.stream()
                        .filter(d -> d.getCalories() > 300)
                        .limit(3) // 限定数量
                        .collect(toList());

        dishesLimit3.forEach(System.out::println);

        // 跳过
        List<Dish> dishesSkip2 =
                menu.stream()
                        .filter(d -> d.getCalories() > 300)
                        .skip(2)
                        .collect(toList());

        dishesSkip2.forEach(System.out::println);

    }
}
