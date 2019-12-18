package me.xhy.java.lang.java8.nc1Lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created by xuhuaiyu-macpro on 2017/3/6.
 * <p>
 * 为什么不能对 Arrays.asList() 返回的引用进行 add 和 remove
 */
public class C7NotedArrayAsListOnlyRead {

  public static void main(String[] args) {

    ArrayList listRight = new ArrayList<>(Arrays.asList("1", "2", "3"));
    // 更智能的自动类型推断，定义 和 实现 都没有显示的泛型
    listRight.add("bingo");
    System.out.println(listRight);

    List<String> listWrong = asList("1", "", "2");
    listWrong.add("can not be success, must be throw exception, UnsupportedOperationException, but why");

		/*
        为什么不能对 Arrays.asList() 返回的引用进行 add 和 remove
		1. 追踪 "asList()"代码 到 Arrays 类的 asList 方法上， 方法实现为 :
			return new ArrayList<>(a);
		2. 查看该 ArrayList 类型定义， 发现是 Arrays 的一个内部类 :
			private static class ArrayList<E> extends AbstractList<E>
				implements RandomAccess, java.io.Serializable {}
			在该内部类中， 没有定义 add() remove() 方法
		3. 追踪父类 AbstractList
			发现方法有 add() 和 remove() 的定义并且有实现
            方法体内实现均为 throw new UnsupportedOperationException();

        案件告破，这就是为什么 Arrays.asList() 的返回结果不能进行 add 和 remove


		如果避免
		List<Integer> list = new ArrayList<>(Arrays.asList("1","2","3"));
		 */


  }
}
