package me.xhy.java.lang.java9.s3Exception;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * @author xxx
 * @since 2022-04-26 15:13
 */
public class S1E {

  public static void main(String[] args) {

  }

  // java 7 以前
  public void read1() {
    InputStreamReader reader = null;
    try {
      reader = new InputStreamReader(System.in);
      int read = reader.read();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      // 关闭操作
      if(null != reader) {
        try {
          reader.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  // java 7 自动关闭
  public void read2() {
    try(InputStreamReader reader = new InputStreamReader(System.in)) {
      int read = reader.read();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // java 9 支持
  public void read3() {
    /*final*/InputStreamReader reader = new InputStreamReader(System.in);
    OutputStreamWriter writer = new OutputStreamWriter(System.out);
    try(reader; writer) { // 自动关闭，java 8 及以前版本会报错
      // 在 try(reader) 中， reader 将成为 final 的，是默认行为，因为要知道关闭的资源，不能再重新指定对象
      int read = reader.read();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
