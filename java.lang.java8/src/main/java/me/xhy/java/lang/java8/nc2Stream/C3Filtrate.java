package me.xhy.java.lang.java8.nc2Stream;

import me.xhy.java.lang.materials.dish.Dish;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

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

        System.out.println("全部内容 ： ");
        menu.stream().filter(d -> d.getCalories() > 300).forEach(System.out::println);
        // 3. 截短
        List<Dish> dishesLimit = menu.stream()
                .filter(d -> d.getCalories() > 300)
                .limit(3) // 限定数量，返回值范围 ： 0-limit
                .collect(toList());
        System.out.println("保留前三个 ： ");
        dishesLimit.forEach(System.out::println);

        // 跳过
        List<Dish> dishesSkip = menu.stream()
                .filter(d -> d.getCalories() > 300)
                .skip(3)
                .collect(toList());
        System.out.println("忽略全三个 ： ");
        dishesSkip.forEach(System.out::println);

        // 截取中间部分 保留 4 5 6
        List<Dish> leaveMiddle = menu.stream()
                .filter(d -> d.getCalories() > 300)
                .skip(3)
                .limit(3)
                .collect(toList());
        System.out.println("保留第 4 5 6 （下标 3 4 5）： ");
        leaveMiddle.forEach(System.out::println);

        // 忽略中间部分 忽略第 4 5 6
        System.out.println("忽略中间部分 忽略第 4 5 6");
        Stream<Dish> menuStream1 = menu.stream()
                .filter(d -> d.getCalories() > 300);
        Stream<Dish> menuStream2 = menu.stream()
                .filter(d -> d.getCalories() > 300);
        Stream.concat(menuStream1.limit(3), menuStream2.skip(3 + 3))
                .forEach(System.out::println);

    }
}
