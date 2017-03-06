package me.xhy.java.lang.java8.nc1Lambda.old;

import me.xhy.java.lang.materials.fruit.Apple;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuhuaiyu on 2017/2/7.
 */
public class C3WithoutImportantApi {

    public static void main(String[] args) {
        List<Apple> inventory = Apple.getSomeApples();

        List<Apple> greenApples = filterGreenApples(inventory);
        List<Apple> heavyApples = filterHeavyApples(inventory);

        // 软件工程中，复制粘贴是很可怕的，当逻辑发生变化，改了一个却忘记了改另外一个。

    }


    public static List<Apple> filterGreenApples(List<Apple> inventory) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if ("green".equals(apple.getColor())) { // 只有 "green".equals(apple.getColor()) 是有效代码，其他均是样板代码
                result.add(apple);
            }
        }

        return result;
    }

    public static List<Apple> filterHeavyApples(List<Apple> inventory) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (apple.getWeight() > 150) { // 只有 apple.getWeight() > 150 是有效代码
                result.add(apple);
            }
        }

        return result;
    }
}
