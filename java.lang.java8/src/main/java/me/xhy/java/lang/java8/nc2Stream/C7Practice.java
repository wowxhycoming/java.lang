package me.xhy.java.lang.java8.nc2Stream;

import me.xhy.java.lang.materials.trade.Trader;
import me.xhy.java.lang.materials.trade.Transaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

        // 按名字排序所有交易员的名字字符串中的字符
        String sortedNameChar = transactions.stream()
                .map(txn -> txn.getTrader().getName())
                .flatMap(name -> Arrays.stream(name.split("")))// char[]
                .distinct()
                .sorted()
                .reduce("", (s1, s2) -> s1 + "|" + s2);
        System.out.println(sortedNameChar);
        String sortedNameChar2 = transactions.stream()
                .flatMap(txn -> Arrays.stream(txn.getTrader().getName().split("")))
                .distinct()
                .sorted()
                .reduce("", (s1, s2) -> s1 + "|" + s2);
        System.out.println(sortedNameChar2);

        // 有没有交易员在米兰工作
        boolean milanBased =
                transactions.stream()
                        .anyMatch(transaction -> transaction.getTrader()
                                .getCity()
                                .equals("Milan")
                        );
        System.out.println(milanBased);


        // 因为会改变 transaction 数据，所以拷贝一份
        List<Transaction> txnList1 = new ArrayList(transactions);
        List<Transaction> txnList2 = new ArrayList(transactions);
        // 这种拷贝方法是错误的， txnList1 == txnList2 的值为 false，只能说明两个集合的引用的地址不同，不能说明里面装的对象的地址不同
        // 对 txnList1 里的元素进行操作 等同于 txnList2 里的元素做操作， 因为元素指针是一样的
        System.out.println(txnList1.get(0) == txnList2.get(0));

        // 把所有米兰的交易更新成剑桥
        System.out.println("更改前原值 ： ");
        System.out.println(txnList1);
        txnList1.stream()
                .map(Transaction::getTrader)
                .filter(trader -> trader.getCity().equals("Milan"))
                .forEach(trader -> trader.setCity("Cambridge"));
        System.out.println("使用更改方法1 ： ");
        System.out.println(txnList1);

        // 都是 Cambridge 了，都改成 Milan
        System.out.println("更改前原值 ： ");
        System.out.println(txnList2);
        txnList2.stream()
                .map(txn -> {
                    if (txn.getTrader().getCity().equals("Cambridge"))
                        txn.getTrader().setCity("Milan");
                    return txn;
                }).forEach(txn -> {
        });
        System.out.println("使用更改方法2 ： ");
        System.out.println(txnList2);
        // 两个方法只是从不同纬度进行修改， forEch 是为了让中间操作执行

        // 交易额最高的交易
        int highestValue =
                transactions.stream()
                        .map(Transaction::getValue)
                        .reduce(0, Integer::max);
        System.out.println(highestValue);

        // Stream 的反转
        // 查看了 Stream 提供的方法，没有反转操作；Stream 从 Collection 来，如果 Collection 能反转即可
        System.out.println("原来的顺序：");
        transactions.forEach(System.out::println);
        System.out.println("反转后的顺序");
        Collections.reverse(transactions);
        transactions.forEach(System.out::println);

    }

}
