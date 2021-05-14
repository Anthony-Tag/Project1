package org.menu.model;

public class Account {
    private int id;
    private int user_id;
    private int account_number;
    private double balance;


    public Account(int id, int user_id, int account_number, double balance) {
        this.id = id;
        this.user_id = user_id;
        this.account_number = account_number;
        this.balance = balance;
    }

    public Account() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getAccount_number() {
        return account_number;
    }

    public void setAccount_number(int account_number) {
        this.account_number = account_number;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", account_number=" + account_number +
                ", balance=" + balance +
                '}';
    }
}
