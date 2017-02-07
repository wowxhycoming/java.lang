package me.xhy.java.lang.java8.nc1Lambda;

import me.xhy.java.lang.java8.nc1Lambda.old.C2HarderFilterOldEx;
import me.xhy.java.lang.materials.data.Data;

import java.util.List;

/**
 * Created by xuhuaiyu on 2017/2/7.
 */
public class C2HarderFilter {

	public static void main(String[] args) {

		Data data = new Data();
		C2HarderFilterOldEx c = new C2HarderFilterOldEx();

		List<Object> list = data.initData();
		List<Integer> integers = c.choseInteger(list, o -> {
			if (o instanceof Integer) return true;
			return false;
		});

		integers.forEach(System.out::println);

	}
}
