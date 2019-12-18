package me.xhy.java.lang.java5.nc2Concurrent;

import java.util.concurrent.ThreadFactory;
import java.util.function.Function;

/**
 * Created by xuhuaiyu-macpro on 2017/3/22.
 */
public class _ThreadFactorySupport {

  // String -> ThreadFactory
  public static Function<String, ThreadFactory> threadFactoryFunction =
      s -> r -> new Thread(r, s + " : " + _AtomicSupport.i.getAndIncrement());

  public static ThreadFactory getThreadFactory(Function<String, ThreadFactory> f, String prefix) {
    return f.apply(prefix);
  }

  public static ThreadFactory getThreadFactory(String prefix) {
    return threadFactoryFunction.apply(prefix);
  }


}
