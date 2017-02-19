package me.xhy.java.lang.java8.nc3Collector;

import me.xhy.java.lang.materials.trade.TradeData;
import me.xhy.java.lang.materials.trade.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

/**
 * Created by xuhuaiyu on 2017/2/19.
 *
 * 对比
 */
public class C1Contrast {

    public static void main(String[] args) {

        // 按货币分组交易
        // init data
        List<Transaction> transactionList = TradeData.currencyAndValueTxn;
        // result
        Map<Transaction.Currency, List<Transaction>> result = new HashMap<>();

        // old
        for(Transaction transaction : transactionList) {
            Transaction.Currency currency = transaction.getCurrency();
            List<Transaction> transactionsForCurrency = result.get(currency);
            if(transactionsForCurrency == null) {
                transactionsForCurrency = new ArrayList<>();
            }
            transactionsForCurrency.add(transaction);
            result.put(currency, transactionsForCurrency);
        }
        System.out.println(result);

        // stream
        Map<Transaction.Currency, List<Transaction>> streamSolveResult = transactionList.stream().collect(groupingBy(Transaction::getCurrency));
        System.out.println(streamSolveResult);

    }

}
