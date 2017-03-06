package me.xhy.java.lang.java8.nc3Collector;

import me.xhy.java.lang.materials.dish.CaloricLevel;
import me.xhy.java.lang.materials.dish.Dish;

import java.util.*;

import static java.util.Comparator.*;
import static java.util.stream.Collectors.*;

/**
 * Created by xuhuaiyu on 2017/2/19.
 * <p>
 * 分组
 */
public class C3Gourping {
    public static void main(String[] args) {
        List<Dish> dishes = Dish.menu;

        // 单级分组 Dish::getType
        Map<Dish.Type, List<Dish>> dishesByType = dishes.stream().collect(groupingBy(Dish::getType));
        System.out.println("单级分组 Dish::getType : \r\n" + dishesByType);

        // 自定义策略分组，最好写成函数引用方式
        Map<CaloricLevel, List<Dish>> dishesByCaloric = dishes.stream().collect(groupingBy(
                dish -> {
                    if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                    else if (dish.getCalories() < 700) return CaloricLevel.NORMAL;
                    else return CaloricLevel.FAT;
                }
        ));
        System.out.println("自定义策略分组，按 caloric 等级 : \r\n" + dishesByCaloric);

        // 多级分组 先按类型，在按 caloric 等级
        Map<Dish.Type, Map<CaloricLevel, List<Dish>>> dishesByTypeCaloric =
                dishes.stream().collect(groupingBy(Dish::getType, groupingBy(
                        dish -> {
                            if (dish.getCalories() <= 401) return CaloricLevel.DIET;
                            else if (dish.getCalories() < 701) return CaloricLevel.NORMAL;
                            else return CaloricLevel.FAT;
                        }
                )));
        System.out.println("多级分组 : \r\n" + dishesByTypeCaloric);

        // 按子组收集 归约（条目总数）
        Map<Dish.Type, Long> typeCount =
                dishes.stream().collect(groupingBy(Dish::getType, counting()));
        System.out.println("按子组收集 归约（条目总数） :\r\n" + typeCount);

        // 按子组收集 归约（最大值）
        Map<Dish.Type, Optional<Dish>> mostCaloricByType =
                dishes.stream().collect(groupingBy(
                        Dish::getType,
                        maxBy(comparingInt(Dish::getCalories))
                ));
        System.out.println("按子组收集 归约（最大值）:\r\n" + mostCaloricByType);
        /*
        其实，这里Map中的Optional<Dish>是没有必要的，必须这么写是因为maxBy的返回值类型如此。
        因为第一次收集，如果没有某个类型的Dish，那么这个类型将不会出现在key中，也就是不会有对应的Optional.empty()值。
        所以一定不空的列表maxBy()自然不会出现Optional.empty() ， 也就不必使用 Optional<Dish>
         */

        // 把收集器的结果转换成另一种类型
        // 因为分组操作的 Map 结果集中的值都被包装成了 Optional ， 这并没有什么卵用，所以要去掉这一层包装
        Map<Dish.Type, Dish> realValueMostCaloricByType =
                dishes.stream()
                        .collect(
                                groupingBy(
                                        Dish::getType,
                                        collectingAndThen(
                                                maxBy(comparingInt(Dish::getCalories)),
                                                Optional::get)
                                ));
        System.out.println("去掉 Optional 包装 ： \r\n" + realValueMostCaloricByType);

        // 与 groupingBy 一起联合使用的例子
        // 求出每种类型菜肴的热量总和
        Map<Dish.Type, Integer> sumCaloriesByType =
                dishes.stream()
                        .collect(
                                groupingBy(
                                        Dish::getType,
                                        summingInt(Dish::getCalories)
                                )
                        );
        System.out.println("每种菜品的热量总和 :\r\n" + sumCaloriesByType);

        // 每种类型的菜种都包含哪些热量等级
        Map<Dish.Type, Set<CaloricLevel>> groupCaloricLevelType =
                dishes.stream()
                        .collect(
                                groupingBy(
                                        Dish::getType,
                                        mapping(
                                                dish -> {
                                                    if (dish.getCalories() <= 401) return CaloricLevel.DIET;
                                                    else if (dish.getCalories() <= 701) return CaloricLevel.NORMAL;
                                                    else return CaloricLevel.FAT;
                                                }, toSet()
                                        )
                                )
                        );
        System.out.println("每种菜中都有哪些热量等级 :\r\n" + groupCaloricLevelType);

        groupCaloricLevelType =
                dishes.stream()
                        .collect(
                                groupingBy(
                                        Dish::getType,
                                        mapping(
                                                dish -> {
                                                    if (dish.getCalories() <= 402) return CaloricLevel.DIET;
                                                    else if (dish.getCalories() <= 702) return CaloricLevel.NORMAL;
                                                    else return CaloricLevel.FAT;
                                                }, toCollection(HashSet::new) // 使用收集器接口
                                        )
                                )
                        );
        System.out.println("== \r\n" + groupCaloricLevelType);

        // 将上面结果中， 每种热量的菜列出来， 如果减肥的我想要吃鱼应该很容易在下面结果中选择菜肴（按菜肴种类 和 热量等级显示菜单）
        Map<Dish.Type, Map<CaloricLevel, List<Dish>>> groupDishByTypeAndCL =
                dishes.stream()
                        .collect(
                                groupingBy(
                                        Dish::getType,
                                        groupingBy(
                                                dish -> {
                                                    if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                                                    else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                                                    else return CaloricLevel.FAT;
                                                }, toList()
                                        )
                                )
                        );
        System.out.println("按菜肴种类 和 热量等级显示菜单 :\r\n" + groupDishByTypeAndCL);


    }
}
