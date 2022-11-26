package me.xhy.java.lang.java9.s1InterfaceFunction;

/**
 * @author xxx
 * @since 2022-04-24 16:14
 */
public class S2IFImpl implements S1InterfaceFunction {

  // methodA 是抽象方法必须实现； methodC 是默认方法，可以重写；其他方法不能重写
  @Override
  public void methodA() {

  }

  public static void main(String[] args) {
    S2IFImpl impl = new S2IFImpl();
    impl.methodC(); // 接口内部 ，methodC 调用了 methodD
  }


}
