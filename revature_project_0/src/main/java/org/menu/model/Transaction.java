package org.menu.model;

public class Transaction {
    private int account_number;
    private double amount;
    private int user;
    private int transaction_number;

    public Transaction(int account_number, double amount, int user, int transaction_number) {
        this.account_number = account_number;
        this.amount = amount;
        this.user = user;
        this.transaction_number = transaction_number;
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

    public int getTransaction_number() {
        return transaction_number;
    }

    public void setTransaction_number(int transaction_number) {
        this.transaction_number = transaction_number;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "account_number=" + account_number +
                ", amount=" + amount +
                ", user=" + user +
                ", transaction_number=" + transaction_number +
                '}';
    }
}
