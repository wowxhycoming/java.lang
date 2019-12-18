package me.xhy.java.lang.concurrent.s4lock;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xuhuaiyu-macpro on 2017/3/30.
 */
public class S4Exchanger {

  public static void main(String[] args) {
    Exchanger<String> exchanger = new Exchanger<>();
    ExecutorService pool = Executors.newCachedThreadPool();

    Car car = new Car(exchanger);
    Bike bike = new Bike(exchanger);

    car.start();
    bike.start();

    System.out.println("Main end!");
  }

}

class Car extends Thread {
  private Exchanger<String> exchanger;

  public Car(Exchanger<String> exchanger) {
    super("Car Thread : ");
    this.exchanger = exchanger;
  }

  @Override
  public void run() {
    try {
      System.out.println(Thread.currentThread().getName() + ": " + exchanger.exchange("Car"));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}

class Bike extends Thread {
  private Exchanger<String> exchanger;

  public Bike(Exchanger<String> exchanger) {
    super("Bike Thread : ");
    this.exchanger = exchanger;
  }

  @Override
  public void run() {
    try {
      System.out.println(Thread.currentThread().getName() + ": " + exchanger.exchange("Bike"));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}