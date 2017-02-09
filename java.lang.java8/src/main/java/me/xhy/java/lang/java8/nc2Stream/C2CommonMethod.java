package me.xhy.java.lang.java8.nc2Stream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Character.isDigit;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

/**
 * Created by xuhuaiyu on 2017/2/8.
 * 常用操作
 */
public class C2CommonMethod {

    public static void main(String[] args) {

        // toList ，从 Stream 生成一个列表
        List<String> collected = Stream.of("a", "b", "c", "d").collect(Collectors.toList());
        collected.forEach(System.out::println);

        // map - for
        collected = new ArrayList<>();
        for (String s : asList("a", "b", "c", "d")) {
            String uppercaseString = s.toUpperCase();
            collected.add(uppercaseString);
        }
        for(String s : collected) {
            System.out.println(s);
        }

        // map 的参数为 Function<T, R> T R ， 生成一个新的值代替 Stream 中的值
        collected = Stream.of("a", "b", "c", "d").map(s -> s.toLowerCase()).collect(toList());
        collected.forEach(System.out::println);
        collected = collected.stream().map(String::toUpperCase).collect(toList());
        collected.forEach(System.out::println);

        // filter 的参数为 Predicate<T> T boolean
        collected = Stream.of("1a", "b", "c", "d").filter(e -> isDigit(e.charAt(0))).collect(toList());
        collected.forEach(System.out::println);

        // flatMap 的参数为
        // 非扁平化处理
        List<List<Integer>> togetherFalse = Stream.of(asList(1,2), asList(3,4)).collect(toList());
        togetherFalse.forEach(e -> System.out.println(e.getClass().getName()));
        // 扁平化处理
        List<Integer> together = Stream.of(asList(1,2), asList(3,4)).flatMap(member -> member.stream()).collect(toList());
        together.forEach(System.out::println);





    }


}
