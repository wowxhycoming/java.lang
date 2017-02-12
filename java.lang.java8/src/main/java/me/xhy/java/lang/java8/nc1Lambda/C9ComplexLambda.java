package me.xhy.java.lang.java8.nc1Lambda;

import me.xhy.java.lang.materials.fruit.Apple;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import static java.util.Comparator.comparing;

/**
 * Created by xuhuaiyu on 2017/2/12.
 *
 * 复合 Lambda 表达式
 */
public class C9ComplexLambda {

    public static List<Apple> inventory = Apple.getSomeApples();

    public static void main(String[] args) {
        // 1. 比较器复合
        // 1.1. 逆序
        inventory.sort(Comparator.comparing(Apple::getWeight).reversed());

        // 1.2.比较器链 ： 当两个苹果重量一样时，使用颜色排序
        inventory.sort(comparing(Apple::getWeight).reversed().thenComparing(Apple::getColor));

        // 2. 谓词复合
        Predicate<Apple> redApple = apple -> "red".equals(apple.getColor());
        Predicate<Apple> notRedApple = redApple.negate(); // redApple 的非

        Predicate<Apple> redAndHeavyOrGreen =
                redApple.and(apple -> apple.getWeight() > 150)
                        .or(apple -> "green".equals(apple.getColor()));
        // 表达式的优先级是按从左到右确定的，比如 a.or(b).and(c) 是 (a || b) && c

        // 3. 函数复合
        // Function 的 andThen 和 compose 方法


    }
}
