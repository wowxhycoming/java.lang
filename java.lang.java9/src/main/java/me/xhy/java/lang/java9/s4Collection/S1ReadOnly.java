package me.xhy.java.lang.java9.s4Collection;

import jdk.management.resource.ResourceType;

import java.util.*;

/**
 * @author xxx
 * @since 2022-04-28 14:36
 */
public class S1ReadOnly {
  public static void main(String[] args) {
    // java 8
    List<String> list = new ArrayList<>();
    list.add("1");
    list.add("2");
    list.add("3");

    List<String> readOnly = Collections.unmodifiableList(list);
    // 抛异常
    try {
      readOnly.add("4");
    } catch (Exception e) {
      e.printStackTrace();
    }
    readOnly.forEach(System.out::println);

    // 可以更换引用指向的对象
    readOnly = new ArrayList<>();
    readOnly.add("new");
    readOnly.forEach(System.out::println);

    Set<Integer> readOnlySet = Set.of(1, 2, 3);
    readOnlySet.forEach(System.out::println);

    try {
      readOnlySet.add(4);
    } catch (Exception e) {
      System.out.println("不能向 set 中添加");
      e.printStackTrace();
    }
    readOnlySet.forEach(System.out::println);

    System.out.println("===== java 9 =====");
    // java 9 创建只读
    List<Integer> list2 = List.of(1,2,3);

    try {
      list2.add(4);
    } catch (Exception e) {
      e.printStackTrace();
    }
    list2.forEach(System.out::println);

  }


}
