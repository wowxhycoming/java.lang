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

    public static List<Transaction> currencyAndValueTxn =
            Arrays.asList( new Transaction(Transaction.Currency.EUR, 1500 ),
                            new Transaction(Transaction.Currency.USD, 2300 ),
                            new Transaction(Transaction.Currency.GBP, 9900 ),
                            new Transaction(Transaction.Currency.EUR, 1100 ),
                            new Transaction(Transaction.Currency.RMB, 7800 ),
                            new Transaction(Transaction.Currency.CHF, 6700 ),
                            new Transaction(Transaction.Currency.EUR, 5600 ),
                            new Transaction(Transaction.Currency.USD, 4500 ),
                            new Transaction(Transaction.Currency.CHF, 3400 ),
                            new Transaction(Transaction.Currency.GBP, 3200 ),
                            new Transaction(Transaction.Currency.USD, 4600 ),
                            new Transaction(Transaction.Currency.RMB, 5700 ),
                            new Transaction(Transaction.Currency.EUR, 6800 ) );
}
