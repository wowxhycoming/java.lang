package me.xhy.java.lang.java8.nc3Collector;

import me.xhy.java.lang.materials.dish.Dish;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;

/**
 * Created by xuhuaiyu on 2017/2/26.
 * <p>
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
        System.out.println(isPrime(7));
        Map<Boolean, List<Integer>> primePartition =
            IntStream.range(1, 1000).boxed().collect(partitioningBy(C4Partition::isPrime));
        System.out.println(primePartition);

    }

    // 计算传入参数是否为素数
    public static boolean isPrime(int end) {
        return IntStream.range(2, end).noneMatch(
            i -> {
//                System.out.println(i);
                return end % i == 0; // 执行end%2|end%3|...|end%end-1 ，当返回true，中断，外层表达式既为假
            }
        );
    }

}
