package me.xhy.java.lang.java8.nc1Lambda;

import me.xhy.java.lang.materials.fruit.Apple;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuhuaiyu on 2017/2/7.
 */
public class C5UseLambdaAppleGreenPredicate implements C5UseLambdaApplePredicate {
    @Override
    public boolean test(Apple apple) {
        return "green".equals(apple.getColor());
    }
}
