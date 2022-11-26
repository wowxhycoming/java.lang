package me.xhy.java.lang.java9.s2DiamondOperator;

import java.util.HashSet;
import java.util.Set;

/**
 * @author xxx
 * @since 2022-04-26 15:07
 */
public class S1DO {

  public static void main(String[] args) {
    diamondOperator1();
  }

  // 钻石操作符的使用升级
  public static void diamondOperator1() {
    Set<String> set = new HashSet<>()/*{}*/;
    set.add("1");
    set.add("2");
    set.add("3");
    set.add("4");
    set.add("5");

    set.forEach(System.out::println);
  }

  public static void diamondOperator2() {
    // 这里的{}创建了一个 HashSet 的匿名子类对象，可以在 {} 内填充静态代码块 或 属性
    Set<String> set = new HashSet<>(){};
    set.add("1");
    set.add("2");
    set.add("3");
    set.add("4");
    set.add("5");

    set.forEach(System.out::println);
  }




}
