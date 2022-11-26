package me.xhy.java.lang.java8.nc6DateTime;

import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * @author xxx
 * @since 2022-04-21 19:59
 */
public class C6Zone {
  public static void main(String[] args) {
    // 所有时区
    ZoneId.getAvailableZoneIds().forEach(System.out::println);
    System.out.println("系统默认时区：" + ZoneId.systemDefault());

    // 北京时间时区
    LocalDateTime ldt = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
    System.out.println(ldt);

    ZonedDateTime zdt = LocalDateTime.now().atZone(ZoneId.of("Asia/Shanghai"));
    System.out.println(zdt);

  }
}
