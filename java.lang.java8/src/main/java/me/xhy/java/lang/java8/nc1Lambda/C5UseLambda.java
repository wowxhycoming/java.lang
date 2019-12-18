package me.xhy.java.lang.java8.nc1Lambda;

import me.xhy.java.lang.java8.nc1Lambda.old.C2HarderFilterOldEx;
import me.xhy.java.lang.java8.nc1Lambda.old.C3WithoutImportantApi;
import me.xhy.java.lang.materials.data.Data;
import me.xhy.java.lang.materials.fruit.Apple;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuhuaiyu on 2017/2/7.
 * <p>
 * 如何面对不停变化的需求
 */
public class C5UseLambda {

  public static void main(String[] args) {

    List<Apple> inventory = Apple.getSomeApples();

    // 1. 只能筛选绿色的苹果
    List<Apple> greenApples = C3WithoutImportantApi.filterGreenApples(inventory);

    // 2. 那还要筛选其他颜色的苹果怎么办
    List<Apple> colorApples = filterApplesByColor(inventory, "red");

    // 3. 能直接写出按重量筛选苹果的代码，于是拷贝的按颜色筛选苹果的代码
    List<Apple> weightApples = filterApplesByWeight(inventory, 150);

    // 4. 如果 想按所有属性筛选苹果呢 .... ， 如果属性非常多，要组合筛选呢
    // 按每个属性写一个方法，然后不同的调用；或者写一个包含所有筛选属性参数的方法。不写了，会恶心。

    // 5. 设计一个筛选苹果的接口，然后按需求来编写实现类：
    //  1. 设计一个函数式接口；2. 设计一个应用函数式接口的方法，来接受传递的方法，最终完成筛选。这看起来有点像策略模式
    List<Apple> someConditionApples = filterApples(inventory, new C5UseLambdaAppleGreenPredicate());
    someConditionApples = filterApples(inventory, new C5UseLambdaAppleHeavyPredicate());
    // 这里，把筛选苹果的方法参数化了。

    // 6. 写了这么多代码，可能定义了只会用到一次的实现类。
    //  使用匿名类，但是仍然不够好，样板代码一点都没减少，不同的地方仍然是方法体里面的东西

    // 7. 上一种解决方案趋近完美，但是有过多的样板代码。有用的就是那么一句话。
    //  行为参数化，试试使用lambda。
    List<Apple> result = filterApples(inventory, (Apple apple) -> "green".equals(apple.getColor()));

    // 8. 将List类型抽象化，搞定所有水果的筛选，甚至更多
    List<Integer> numbers = new C2HarderFilterOldEx().choseInteger(Data.getSomeData(), o -> o instanceof Integer);

    // 类型检查、函数描述符、@FunctionalInterface
    List<Integer> evenNumbers = filter(numbers, i -> i % 2 == 0);
    evenNumbers.stream().forEach(System.out::println);

  }

  // 只能选择绿色的苹果
  // C3WithoutImportantApi.

  // 更加先进的 按颜色筛选苹果
  public static List<Apple> filterApplesByColor(List<Apple> inventory, String color) {
    List<Apple> result = new ArrayList<>();

    for (Apple apple : inventory) {
      if (apple.getColor().equals(color)) {
        result.add(apple);
      }
    }

    return result;
  }

  // 按重量筛选苹果
  public static List<Apple> filterApplesByWeight(List<Apple> inventory, int weight) {
    List<Apple> result = new ArrayList<>();

    for (Apple apple : inventory) {
      if (apple.getWeight() > weight) {
        result.add(apple);
      }
    }

    return result;
  }

  // 通过不同的实现类作为参数来完成对苹果的筛选
  public static List<Apple> filterApples(List<Apple> inventory, C5UseLambdaApplePredicate p) {
    List<Apple> result = new ArrayList<>();

    for (Apple apple : inventory) {
      if (p.test(apple)) result.add(apple);
    }

    return result;
  }

  // 现在，可以搞定所有水果的筛选了
  public static <T> List<T> filter(List<T> inventory, C5UseLambdaPredicate<T> p) {
    List<T> result = new ArrayList<>();

    for (T e : inventory) {
      if (p.test(e)) result.add(e);
    }

    return result;
  }
}
