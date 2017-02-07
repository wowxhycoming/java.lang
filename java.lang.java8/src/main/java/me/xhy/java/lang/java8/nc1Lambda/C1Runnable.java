package me.xhy.java.lang.java8.nc1Lambda;

/**
 * Created by xuhuaiyu on 2017/2/7.
 */
public class C1Runnable {

	public static void main(String[] args) {

		// 直接传入lambda
		new Thread(() -> System.out.println("this is a lambda")).run();

		// 按函数式接口传入
		Runnable runnableVar = () -> System.out.println("this is a lambda var");
		new Thread(runnableVar).run();

		// lambda 是一种传入行为的思想
	}
}
