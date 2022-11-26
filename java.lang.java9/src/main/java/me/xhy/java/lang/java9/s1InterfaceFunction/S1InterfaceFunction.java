package me.xhy.java.lang.java9.s1InterfaceFunction;

/**
 * @author xxx
 * @since 2022-04-24 14:56
 */
public interface S1InterfaceFunction {

  // java 7:只能声明全局变量（public static final） 和抽象方法 (public abstract)
  void methodA();

  // java 8:生命静态方法 和 默认方法；接口中的方法可以有方法体（例 Collection 接口中的方法实现）
  static void methodB() { // 修饰符 默认 public，其他修饰符会报错
    System.out.println("methodB");
  }

  default void methodC() {
    System.out.println("methodC");
    methodD();
  }

  // java 9:私有方法
  private void methodD(){
    System.out.println("methodD");
  }
}
