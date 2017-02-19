package me.xhy.java.lang.java8.nc3Collector;

import me.xhy.java.lang.materials.dish.CaloricLevel;
import me.xhy.java.lang.materials.dish.Dish;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.*;

/**
 * Created by xuhuaiyu on 2017/2/19.
 *
 * 分组
 */
public class C3Gourping {
    public static void main(String[] args) {
        List<Dish> dishes = Dish.menu;

        Map<Dish.Type, List<Dish>> dishesByType = dishes.stream().collect(groupingBy(Dish::getType));
        System.out.println(dishesByType);

        Map<CaloricLevel, List<Dish>> dishesByCaloric = dishes.stream().collect(groupingBy(
                dish -> {
                    if(dish.getCalories() <= 400) return CaloricLevel.DIET;
                    else if(dish.getCalories() < 700) return CaloricLevel.NORMAL;
                    else return CaloricLevel.FAT;
                }
        ));

        // 多级分组
        Map<Dish.Type, Map<CaloricLevel, List<Dish>>> dishesByTypeCaloric =
                dishes.stream().collect(groupingBy(Dish::getType, groupingBy(
                        dish -> {
                            if(dish.getCalories() <= 401) return CaloricLevel.DIET;
                            else if(dish.getCalories() < 701) return CaloricLevel.NORMAL;
                            else return CaloricLevel.FAT;
                        }
                )));
        System.out.println(dishesByTypeCaloric);

        // 按子组收集
        Map<Dish.Type, Long> typeCount = dishes.stream().collect(groupingBy(Dish::getType, counting()));
        System.out.println(typeCount);

        Map<Dish.Type, Optional<Dish>> mostCaloricByType =
                dishes.stream().collect(groupingBy(
                        Dish::getType,
                        maxBy(Comparator.comparingInt(Dish::getCalories))
                ));
        System.out.println(mostCaloricByType);
        /*
        其实，这里Map中的Optional<Dish>是没有必要的，必须这么写是因为maxBy的返回值类型如此。
        因为第一次收集，如果没有某个类型的Dish，那么这个类型将不会出现在key中，也就是不会有对应的Optional.empty()值。
        所以一定不空的列表maxBy()自然不会出现Optional.empty() ， 也就不必使用 Optional<Dish>
         */


    }
}
