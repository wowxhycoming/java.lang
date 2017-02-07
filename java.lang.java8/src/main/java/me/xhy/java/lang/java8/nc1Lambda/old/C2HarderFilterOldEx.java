package me.xhy.java.lang.java8.nc1Lambda.old;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuhuaiyu on 2017/2/7.
 */
public class C2HarderFilterOldEx {

	public static void main(String[] args) {

		C2HarderFilterOldEx c = new C2HarderFilterOldEx();

		List<Object> list = c.initData();
		List<Integer> integers = c.choseInteger(list, new C2IntegerFilter());

		// 猜测一下结果
		for(Integer i : integers) {
			System.out.println(i);
		}

	}

	public List<Object> initData() {
		// <> 菱形符号 java7 new case , 自动类型判断
		List<Object> list = new ArrayList<>();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(new Integer(100));
		list.add(new Integer(200));

		return list;
	}

	public List<Integer> choseInteger(List<Object> list, C2ObjectFilter filter) {
		List<Integer> result = new ArrayList<>();

		for(Object o : list) {
			if(filter.filter(o)) result.add((Integer) o);
		}

		return result;
	}
}
