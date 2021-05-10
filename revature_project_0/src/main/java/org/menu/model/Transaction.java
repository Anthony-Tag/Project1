package org.menu.model;

public class Transaction {
    private int account_number;
    private double amount;
    private int user;

    public Transaction(int account_number, double amount, int user) {
        this.account_number = account_number;
        this.amount = amount;
        this.user = user;
    }

    public Transaction() {
    }

    public int getAccount_number() {
        return account_number;
    }

    public void setAccount_number(int account_number) {
        this.account_number = account_number;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "account_number=" + account_number +
                ", amount= $" + amount +
                '}';
    }
}
