package me.xhy.java.lang.java8.nc1Lambda;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by xuhuaiyu on 2017/2/11.
 */
@FunctionalInterface
public interface C6BufferedReaderProcessor {
  String process(BufferedReader br) throws IOException;
}
