package me.xhy.java.lang.materials.trade;

public class Transaction {

    private Trader trader;
    private int year;
    private int value;
    private Currency currency;

    public enum Currency {
        EUR, USD, RMB, GBP, CHF
    }

    public Transaction(Trader trader, int year, int value) {
        this.trader = trader;
        this.year = year;
        this.value = value;
    }

    public Transaction(Trader trader, int year, int value, Currency currency) {
        this.trader = trader;
        this.year = year;
        this.value = value;
        this.currency = currency;
    }

    public Transaction(Currency currency, int value) {
        this.value = value;
        this.currency = currency;
    }

    public Trader getTrader() {
        return this.trader;
    }

    public int getYear() {
        return this.year;
    }

    public int getValue() {
        return this.value;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "trader=" + trader +
                ", year=" + year +
                ", value=" + value +
                ", currency=" + currency +
                '}';
    }
}