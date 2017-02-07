package me.xhy.java.lang.java8.nc1Lambda.old;

/**
 * Created by xuhuaiyu on 2017/2/7.
 */
public class C1RunnableOldEx implements Runnable{

	@Override
	public void run() {
		// 大量的样板代码，真正有意义的只是run()方法体内部的代码。
		System.out.println("this is a runnable");
	}

	public static void main(String[] args) {
		new Thread(new C1RunnableOldEx()).run();
	}
}
