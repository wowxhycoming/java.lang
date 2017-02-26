package me.xhy.java.lang.java8.nc3Collector;

import me.xhy.java.lang.materials.dish.Dish;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;

/**
 * Created by xuhuaiyu on 2017/2/26.
 *
 * 分区
 * 分区是分组的特殊情况，由一个谓词作为分组函数（就是分成两组，一真一假）
 */
public class C4Partition {

    public static void main(String[] args) {
        List<Dish> dishes = Dish.menu;

        // 按是否是蔬菜分区
        Map<Boolean, List<Dish>> partitionByVegetarian =
            dishes.stream().collect(partitioningBy(Dish::isVegetarian));
        System.out.println(partitionByVegetarian);

        // 先按是否为蔬菜分区，然后在按类型分组
        Map<Boolean, Map<Dish.Type, List<Dish>>> collectByPartitionAndGroup =
        dishes.stream().collect(
            partitioningBy(
                Dish::isVegetarian,
                groupingBy(Dish::getType)
            )
        );
        System.out.println(collectByPartitionAndGroup);

        // 找到素食和非素食各自热量最高的菜
        Map<Boolean, Dish> mostCaloricDishes =
            dishes.stream()
                .collect(
                    partitioningBy(
                        Dish::isVegetarian,
                        collectingAndThen(
                            maxBy(comparingInt(Dish::getCalories)),
                            Optional::get
                        )
                    )
                );
        System.out.println(mostCaloricDishes);

        // 找出所有素数


isPrime(100);


    }
    public static void isPrime(int end) {
        IntStream.range(3, end).noneMatch(
            i -> {
                System.out.println(i);
                return end % i == 0;
            }
        );
    }

}
