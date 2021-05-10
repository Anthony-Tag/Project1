package org.menu.dao;

import org.menu.exception.BankException;
import org.menu.model.Account;
import org.menu.model.Transaction;
import org.menu.model.User;

import java.util.List;

public interface UserDAO {
    public User getUser(String username, String password) throws BankException;
    public User createUser(String username, String password) throws BankException;
    public List<Account> getARAccount() throws  BankException;
    public void approveAccount(int id) throws BankException;
    public void rejectAccount(int id) throws BankException;
    public List<Account> getCustomerAccount(int id) throws BankException;
    public List<Transaction> getTransactions() throws BankException;
    public void createAccount(double balance, int user_id) throws BankException;//Customer
    public Account getAccount(int account_number) throws BankException;
    public void withdrawalFromAccount(double amount, int account_number, int user_id) throws BankException;
    public void depositToAccount(double amount, int account_number, int user_id) throws BankException;
    public void postMoney(double amount, int account_number1, int account_number2) throws BankException;
    public void acceptMoney(double amount, int account_number1, int account_number2) throws BankException;
}
