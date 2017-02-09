package me.xhy.java.lang.java8.nc1Lambda.old;

/**
 * Created by xuhuaiyu on 2017/2/7.
 */
public class C2IntegerFilter implements C2ObjectFilter {

    @Override
    public boolean filter(Object o) {

        if (o instanceof Integer) return true;
        return false;

    }

}
