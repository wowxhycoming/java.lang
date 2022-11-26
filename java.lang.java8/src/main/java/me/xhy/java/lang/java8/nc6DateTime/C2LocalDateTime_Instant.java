package me.xhy.java.lang.java8.nc6DateTime;

import java.time.*;

/**
 * @author xxx
 * @since 2022-04-20 17:11
 */
public class C2LocalDateTime_Instant {
  public static void main(String[] args) {

    // 1. LocalDate LocalTime LocalDateTime 的使用方式是一样的

    // 当前时间
    LocalDateTime ldt = LocalDateTime.now();
    System.out.println(ldt);

    // 指定时间
    LocalDateTime ldt2 = LocalDateTime.of(2015, 10, 19, 11, 22, 33);
    System.out.println(ldt2);

    // 运算， 会返回一个新的实例
    LocalDateTime ldt3 = ldt.plusYears(2);
    System.out.println(ldt3);

    // 2. Instant 时间戳（unix 元年： 1970年01月01日 00:00:00 到指定时间的毫秒值）
    Instant instant = Instant.now(); // 默认是 UTC 时区
    System.out.println(instant); // 与现实时间会相差8个小时，因为时区不是当前时区 // 2022-04-21T02:34:01.259Z 当前UTC时间

    OffsetDateTime offsetDateTime = instant.atOffset(ZoneOffset.ofHours(8));// 给默认的 UTC 时区加8个小时偏移量
    System.out.println(offsetDateTime); // 2022-04-21T10:34:01.259+08:00 当前本地时区时间







  }
}
