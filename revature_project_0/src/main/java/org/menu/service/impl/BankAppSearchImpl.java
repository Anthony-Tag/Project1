package org.menu.service.impl;

import org.apache.log4j.Logger;
import org.menu.Main;
import org.menu.dao.UserDAO;
import org.menu.dao.impl.UserDAOImpl;
import org.menu.exception.BankException;
import org.menu.model.Account;
import org.menu.model.Transaction;
import org.menu.model.User;
import org.menu.service.BankAppSearch;

import java.util.List;

public class BankAppSearchImpl implements BankAppSearch {
    Logger log = Logger.getLogger(Main.class);
    private UserDAO userDAO=new UserDAOImpl();
    @Override
    public User getUser(String username, String password) throws BankException {
        User user=null;
        if (username == null || password == null){
            log.warn("Invalid username or password");
        }else {
            user = userDAO.getUser(username, password);
        }
        return user;
    }

    @Override
    public User createUser(String username, String password, String type) throws BankException {
        User user=null;
        if (username == null || password == null){
            log.warn("Invalid username or password");
        }else {
            user = userDAO.createUser(username, password, type);
        }
        return user;
    }

    @Override
    public List<Account> getARAccount() throws BankException {
        List<Account> accountList=null;
        accountList = userDAO.getARAccount();
        return accountList;
    }

    @Override
    public void approveAccount(int id) throws BankException {
        if (id<0) {
            log.warn("Invalid id");
        }else{
            userDAO.approveAccount(id);
        }
    }

    @Override
    public void rejectAccount(int id) throws BankException {
        if (id<0) {
            log.warn("Invalid id");
        }else{
            userDAO.rejectAccount(id);
        }
    }

    @Override
    public List<Account> getCustomerAccount(int id) throws BankException{
        List<Account> accountList=null;
        if (id <0){
            log.warn("Invalid Id");
        }else{
            accountList= userDAO.getCustomerAccount(id);
        }
        return accountList;
    }

    @Override
    public List<Transaction> getTransactionsId(int id) throws BankException {
        List<Transaction> transactionList=null;
        transactionList = userDAO.getTransactionsId(id);
        return transactionList;
    }

    @Override
    public List<Transaction> getTransactionsAcc(int account_id) throws BankException {
        List<Transaction> transactionList=null;
        transactionList = userDAO.getTransactionsAcc(account_id);
        return transactionList;
    }

    @Override
    public List<Transaction> getTransactionsTrans(int trans) throws BankException {
        List<Transaction> transactionList=null;
        transactionList = userDAO.getTransactionsTrans(trans);
        return transactionList;
    }

    @Override
    public void createAccount(double balance, int user_id) throws BankException {
        if (balance<49){
            log.warn("Please input an amount greater than $50");
        }else {
            userDAO.createAccount(balance, user_id);
        }
    }

    @Override
    public Account getAccount(int account_number) throws BankException {
        Account account=null;
        if (account_number<0){
            log.warn("Incorrect input");
        }else{
            account= userDAO.getAccount(account_number);
        }
        return account;
    }

    @Override
    public void withdrawalFromAccount(double amount, int account_number, int user_id) throws BankException {
        Account account = userDAO.getAccount(account_number);
        if (account.getBalance()< amount){
            log.warn("Not enough money in account");
            return;
        }
        if (amount < 0 || account_number <0){
            log.warn("Incorrect input");
        }else {
            userDAO.withdrawalFromAccount(amount,account_number, user_id);
        }
    }

    @Override
    public void depositToAccount(double amount, int account_number, int user_id) throws BankException {
        Account account = userDAO.getAccount(account_number);
        if (amount < 0 || account_number <0){
            log.warn("Incorrect input");
        }else {
            userDAO.depositToAccount(amount,account_number, user_id);
        }
    }

    @Override
    public void postMoney(double amount, int account_number1, int account_number2) throws BankException {
        if (amount< 0 | account_number1 < 0| account_number2< 0){
            log.warn("Incorrect input");
        }else{
            userDAO.postMoney(amount, account_number1, account_number2);
        }
    }

    @Override
    public void acceptMoney(double amount, int account_number1, int account_number2) throws BankException {
        if (amount< 0 | account_number1 < 0| account_number2< 0){
            log.warn("Incorrect input");
        }else{
            userDAO.postMoney(amount, account_number1, account_number2);
        }
    }
}
