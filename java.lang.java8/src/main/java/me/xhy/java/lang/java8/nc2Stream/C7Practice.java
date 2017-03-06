package me.xhy.java.lang.java8.nc2Stream;

import me.xhy.java.lang.materials.trade.Trader;
import me.xhy.java.lang.materials.trade.Transaction;

import java.util.List;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static me.xhy.java.lang.materials.trade.TradeData.transactions;

/**
 * Created by xuhuaiyu on 2017/2/12.
 * <p>
 * 实践
 */
public class C7Practice {

    public static void main(String[] args) {
        // 1. 按交易额从小到大排序2011年的交易 （找出2011年的交易，并排序）
        List<Transaction> tr2011 = transactions.stream()
                .filter(transaction -> transaction.getYear() == 2011)
                .sorted(comparing(Transaction::getValue))
                .collect(toList());
        System.out.println(tr2011);

        // 2. 交易员都在哪些城市工作过 （找出所有城市，去重）
        List<String> cities =
                transactions.stream()
                        .map(transaction -> transaction.getTrader().getCity())
                        .distinct()
                        .collect(toList());
        System.out.println(cities);

        // 3. 按姓名排序所有来自剑桥的交易员 （找出所有剑桥的，按名字排序）
        List<Trader> traders =
                transactions.stream()
                        .map(Transaction::getTrader)
                        .filter(trader -> trader.getCity().equals("Cambridge"))
                        .distinct()
                        .sorted(comparing(Trader::getName))
                        .collect(toList());
        System.out.println(traders);


        // 按名字排序所有交易员的名字字符串 （所有交易员的名字字符串，排序，规约）
        String traderStr =
                transactions.stream()
                        .map(transaction -> transaction.getTrader().getName())
                        .distinct()
                        .sorted()
                        .reduce("", (n1, n2) -> n1 + n2);
        System.out.println(traderStr);

        // 有没有交易员在米兰工作
        boolean milanBased =
                transactions.stream()
                        .anyMatch(transaction -> transaction.getTrader()
                                .getCity()
                                .equals("Milan")
                        );
        System.out.println(milanBased);


        // 把所有米兰的交易更新成剑桥
        System.out.println(transactions);
        transactions.stream()
                .map(Transaction::getTrader)
                .filter(trader -> trader.getCity().equals("Milan"))
                .forEach(trader -> trader.setCity("Cambridge"));
        System.out.println(transactions);


        // 交易额最高的交易
        int highestValue =
                transactions.stream()
                        .map(Transaction::getValue)
                        .reduce(0, Integer::max);
        System.out.println(highestValue);
    }

}
