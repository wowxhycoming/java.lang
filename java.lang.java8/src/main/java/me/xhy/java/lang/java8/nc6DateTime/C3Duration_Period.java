package me.xhy.java.lang.java8.nc6DateTime;

import java.time.*;
import java.time.temporal.TemporalAdjuster;
import java.util.concurrent.TimeUnit;

/**
 * @author xxx
 * @since 2022-04-21 10:46
 */
public class C3Duration_Period {
  public static void main(String[] args) throws InterruptedException {

    Instant instant1 = Instant.now();
    TimeUnit.SECONDS.sleep(1);
    Instant instant2 = Instant.now();

    Duration duration = Duration.between(instant1, instant2);
    System.out.println(duration.toMillis());

    LocalTime localTime1 = LocalTime.now();
    TimeUnit.SECONDS.sleep(1);
    LocalTime localTime2 = LocalTime.now();
    System.out.println(Duration.between(localTime1, localTime2).toMillis());

    LocalDate localDate1 = LocalDate.now();
    LocalDate localDate2 = LocalDate.of(2022,1,1);
    Period period = Period.between(localDate1, localDate2);
    System.out.println(period);
    System.out.println(period.getDays());




  }

}
