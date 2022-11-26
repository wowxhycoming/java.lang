package me.xhy.java.lang.java8.nc6DateTime;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author xxx
 * @since 2022-04-21 19:47
 */
public class C5DateTimeFormatter {
  public static void main(String[] args) {
    LocalDateTime localDateTime = LocalDateTime.now();

    System.out.println(DateTimeFormatter.ISO_TIME.format(localDateTime));
    System.out.println(DateTimeFormatter.ISO_DATE.format(localDateTime));
    System.out.println(DateTimeFormatter.ISO_DATE_TIME.format(localDateTime));

    // 自定义解析格式
    DateTimeFormatter myPattern = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss.SSS");
    String formatString = myPattern.format(localDateTime);
    System.out.println(formatString);
    // 自定义格式 还原
    LocalDateTime parse = LocalDateTime.parse(formatString, myPattern);
    System.out.println(parse);

  }
}
