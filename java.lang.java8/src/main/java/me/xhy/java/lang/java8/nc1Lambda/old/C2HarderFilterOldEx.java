package me.xhy.java.lang.java8.nc1Lambda.old;

import me.xhy.java.lang.materials.data.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuhuaiyu on 2017/2/7.
 */
public class C2HarderFilterOldEx {

	public static void main(String[] args) {

		Data data = new Data();
		C2HarderFilterOldEx c = new C2HarderFilterOldEx();

		List<Object> list = data.initData();
		List<Integer> integers = c.choseInteger(list, new C2IntegerFilter());

		// 猜测一下结果
		for(Integer i : integers) {
			System.out.println(i);
		}

	}

	public List<Integer> choseInteger(List<Object> list, C2ObjectFilter filter) {
		List<Integer> result = new ArrayList<>();

		for(Object o : list) {
			if(filter.filter(o)) result.add((Integer) o);
		}

		return result;
	}
}
