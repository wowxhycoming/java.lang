package me.xhy.java.lang.java8.nc1Lambda;

import me.xhy.java.lang.materials.fruit.Apple;

/**
 * Created by xuhuaiyu on 2017/2/7.
 */
public class C5UseLambdaAppleHeavyPredicate implements C5UseLambdaApplePredicate {
  @Override
  public boolean test(Apple apple) {
    return apple.getWeight() > 150;
  }
}
