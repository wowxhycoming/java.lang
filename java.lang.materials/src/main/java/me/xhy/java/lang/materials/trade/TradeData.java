package me.xhy.java.lang.materials.trade;

import java.util.Arrays;
import java.util.List;

/**
 * Created by xuhuaiyu on 2017/2/12.
 */
public class TradeData {

    public static Trader raoul = new Trader("Raoul", "Cambridge");
    public static Trader mario = new Trader("Mario","Milan");
    public static Trader alan = new Trader("Alan","Cambridge");
    public static Trader brian = new Trader("Brian","Cambridge");

    public static List<Transaction> transactions = Arrays.asList(
            new Transaction(brian, 2011, 300),
            new Transaction(raoul, 2012, 1000),
            new Transaction(raoul, 2011, 400),
            new Transaction(mario, 2012, 710),
            new Transaction(mario, 2012, 700),
            new Transaction(alan, 2012, 950)
    );
}
