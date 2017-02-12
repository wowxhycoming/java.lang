package me.xhy.java.lang.java8.nc2Stream;

import me.xhy.java.lang.materials.dish.Dish;

import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

/**
 * Created by xuhuaiyu on 2017/2/12.
 *
 * 映射
 */
public class C4Mapping {

    public static List<Dish> menu = Dish.menu;

    public static void main(String[] args) {

        // 1. map 对每一个元素进行映射
        List<String> dishNames = menu.stream()
                .map(Dish::getName) // 菜肴的名字
//                .map(String::length) // 菜肴名称的长度
                .collect(toList());

        System.out.println(dishNames);

        // 2. 扁平化流
        // map
        List<String> words = asList("Hello", "World");
        List<Integer> wordLengths = words.stream()
                .map(String::length)
                .collect(toList());

        System.out.println(wordLengths);

        // flatMap 如果想处理字符串中的每个字符 ， 得到不重复的字符
        words.stream()
                .flatMap((String line) -> Arrays.stream(line.split("")))
                .distinct()
                .forEach(System.out::print);

        // 返回 [1,2,3] 和 [3,4] 的所有数对
        List<Integer> numbers1 = Arrays.asList(1,2,3,4,5);
        List<Integer> numbers2 = Arrays.asList(6,7,8);
        List<int[]> pairs =
                numbers1.stream()
                        .flatMap((Integer i) -> numbers2.stream()
                                .map((Integer j) -> new int[]{i, j})
                        )
//                        .filter(pair -> (pair[0] + pair[1]) % 3 == 0)
                        .collect(toList());

        pairs.forEach(pair -> System.out.println("(" + pair[0] + ", " + pair[1] + ")"));



    }
}
