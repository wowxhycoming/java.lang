package me.xhy.java.lang.java8.nc6DateTime;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

import static java.time.temporal.ChronoField.*;
import static java.time.temporal.ChronoUnit.WEEKS;

/**
 * @author xxx
 * @since 2022-04-21 16:52
 */

// 时间调节器
public class C4TemporalAdjuster {
  public static void main(String[] args) {
    LocalDateTime localDateTime = LocalDateTime.now();
    System.out.println(localDateTime);

    LocalDateTime localDateTime1 = localDateTime.with(TemporalAdjusters.firstDayOfMonth());
    System.out.println(localDateTime1); // 当月1日

    LocalDateTime localDateTime2 = localDateTime.with(TemporalAdjusters.firstDayOfNextMonth());
    System.out.println(localDateTime2); // 下月1日

    // 自定义 TemporalAdjuster,  它本质是个 FunctionalInterface ;
    // 疑问 ： 为什么 Temporal 类型的 ldt 可以强转成 LocalDateTime
    // 自定义：下个工作日（只参考公历，没有农历假期）

    // 获取当前周的周五
    LocalDateTime fridayThisWeek = localDateTime.with(temporal -> temporal.with(DAY_OF_WEEK, 5));

    LocalDateTime nextWorkDay = localDateTime.with(ldt -> {
      System.out.println(ldt.getClass());
      LocalDateTime l = (LocalDateTime) ldt;

      DayOfWeek dayOfWeek = l.getDayOfWeek();
      if (dayOfWeek.equals(DayOfWeek.FRIDAY)
          || dayOfWeek.equals(DayOfWeek.SATURDAY) || dayOfWeek.equals(DayOfWeek.SUNDAY)) {
        // TemporalAdjusters 中没有下个星期的第一天， lambda实现： 当天周的周一，再加一个星期
        return l.with((temporal) -> temporal.with(DAY_OF_WEEK, 1).plus(1, WEEKS));
      } else return l.plusDays(1);
    });
    System.out.println("今天的下个工作日：" + nextWorkDay);

    nextWorkDay = fridayThisWeek.with(ldt -> {
      LocalDateTime l = (LocalDateTime) ldt;
      DayOfWeek dayOfWeek = l.getDayOfWeek();
      if (dayOfWeek.equals(DayOfWeek.FRIDAY)
          || dayOfWeek.equals(DayOfWeek.SATURDAY) || dayOfWeek.equals(DayOfWeek.SUNDAY)) {
        // TemporalAdjusters 中没有下个星期的第一天， lambda实现： 当天周的周一，再加一个星期
        return l.with((temporal) -> temporal.with(DAY_OF_WEEK, 1).plus(1, WEEKS));
      } else return l.plusDays(1);
    });
    System.out.println("本周五的下个工作日：" + nextWorkDay);

  }
}
