package me.xhy.java.lang.java8.nc3Collector;

import me.xhy.java.lang.materials.dish.Dish;

import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.*;

/**
 * Created by xuhuaiyu on 2017/2/19.
 *
 * 归约 和 汇总
 */
public class C2ReduceAndSummarizing {

    public static void main(String[] args) {
        List<Dish> dishList = Dish.menu;

        // 归约
        long howManyDishes = dishList.stream().count(); // 用流归约
        System.out.println(howManyDishes);
        howManyDishes = dishList.stream().collect(counting()); // 用收集器归约
        System.out.println(howManyDishes);

        // maxBy and minBy
        Comparator<Dish> dishCaloriesComparator = Comparator.comparingInt(Dish::getCalories);
        Optional<Dish> mostCaloriesDish = dishList.stream().collect(maxBy(dishCaloriesComparator));
        System.out.println(mostCaloriesDish.get());

        // 汇总
        // summingInt Long Double  先 map 再 reduce
        int totalCalories = dishList.stream().collect(summingInt(Dish::getCalories));
        System.out.println(totalCalories);

        // averagingInt
        double avgCalories = dishList.stream().collect(averagingInt(Dish::getCalories));
        System.out.println(avgCalories);

        // summarizing 一次取得
        IntSummaryStatistics menuStatistics = dishList.stream().collect(summarizingInt(Dish::getCalories));
        System.out.println(menuStatistics);

        // 连接字符串
        String shotMenu = dishList.stream().map(Dish::getName).collect(joining());
        System.out.println(shotMenu);
        shotMenu = dishList.stream().map(Dish::getName).collect(joining(","));
        System.out.println(shotMenu);
        shotMenu = dishList.stream().map(Dish::getName).collect(joining(",", "[", "]"));
        System.out.println(shotMenu);

        // 广义的归约
        // 初值和累加器 ， 转换函数， 归约算法
        int totalCaloriesSum = dishList.stream().collect(reducing(0, Dish::getCalories, Integer::sum));
        System.out.println(totalCaloriesSum);
        mostCaloriesDish = dishList.stream().collect(reducing((i, j) -> i.getCalories() > j.getCalories() ? i : j));
        System.out.println(mostCaloriesDish.get());

        // 使用reduce实现counting
        Long count = dishList.stream().collect(reducing(0L, e -> 1L, Long::sum));

        // 其他求卡路里的方法
        int cc1 = dishList.stream().mapToInt(Dish::getCalories).sum();

        // 问题 ：下面哪种写法能实现joining()
        /*
        dishList.stream().map(Dish:getName).collect(reducing(s1, s2 -> s1 + s2)).get();
        dishList.stream().collect(reducing((d1, d2) -> di.getName() + d2.getName())).get();
        dishList.stream().collect(reducing("", Dish::getName, (s1, s2) -> s1 + s2));
         */
    }
}
