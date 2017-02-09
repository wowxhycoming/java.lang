package me.xhy.java.lang.materials.fruit;

import java.util.Arrays;
import java.util.List;

/**
 * Created by xuhuaiyu on 2017/2/7.
 */
public class Apple {

    private int weight = 0;
    private String color = "";

    public Apple(int weight, String color) {
        this.weight = weight;
        this.color = color;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String toString() {
        return "Apple{" +
                "color='" + color + '\'' +
                ", weight=" + weight +
                '}';
    }

    public List<Apple> getSomeApples() {

        List<Apple> inventory = Arrays.asList(
                new Apple(80, "green"),
                new Apple(155, "green"),
                new Apple(120, "red"));

        return inventory;
    }

}
