package org.menu.dao.impl;

import org.menu.dao.UserDAO;
import org.menu.dao.dbutil.PostgresSqlConnection;
import org.menu.exception.BankException;
import org.menu.model.Account;
import org.menu.model.Transaction;
import org.menu.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class UserDAOImpl implements UserDAO {
    Scanner scanner = new Scanner(System.in);

    @Override//complete
    public User getUser(String username, String password) throws BankException {
        User user = null;
        try (Connection connection = PostgresSqlConnection.getConnection()) {
            String sql = "select u.username, u.\"password\", u.id, u.type from bank_app.\"user\" u where u.username = ? and u.\"password\"= ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setId(resultSet.getInt("id"));
                user.setType(resultSet.getString("type"));
            } else {
                throw new BankException("No User found with this username and password");
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
            throw new BankException("Internal error occured in getUser... contact SysAdmin");
        }
        return user;
    }

    @Override//complete
    public User createUser(String username, String password) throws BankException {
        User user = null;
        try (Connection connection = PostgresSqlConnection.getConnection()) {
            String sql = "INSERT INTO bank_app.\"user\"\n" +
                    "(username, \"password\", \"type\")\n" +
                    "VALUES(?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, "customer");

            int c = preparedStatement.executeUpdate();
            if (c == 1) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    System.out.println("User account created");
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Internal error in createUser... contact SysAdmin");
        }
        return user;
    }

    @Override
    public List<Account> getARAccount() throws BankException {
        List<Account> accountList = new ArrayList<>();
        int id = 1;
        try (Connection connection = PostgresSqlConnection.getConnection()) {
            String sql = "SELECT account_number, balance, user_id\n" +
                    "FROM bank_app.accounts where id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Account accounts = new Account();
                accounts.setAccount_number(resultSet.getInt("account_number"));
                accounts.setBalance(resultSet.getDouble("balance"));
                accounts.setUser_id(resultSet.getInt("user_id"));
                accountList.add(accounts);
            }
            if (accountList == null) {
                System.out.println("No accounts found");
            } else {
                System.out.println("Accounts found");
                System.out.println(accountList);
            }
            int ch = 0;
            do {
                System.out.println("1) Accept");
                System.out.println("2) Remove");
                System.out.println("3) Exit");
                ch = Integer.parseInt(scanner.nextLine());
                switch (ch) {
                    case 1:
                        System.out.println("Enter the account number of the account you want to accept");
                        id = Integer.parseInt(scanner.nextLine());
                        approveAccount(id);
                        break;
                    case 2:
                        System.out.println("Enter the account number of the account you want to reject");
                        id = Integer.parseInt(scanner.nextLine());
                        rejectAccount(id);
                        break;
                    case 3:
                        break;
                    default:
                        System.out.println("Error has occored");
                }
            } while (ch != 3);

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
        return accountList;
    }

    @Override
    public void approveAccount(int id) throws BankException {

        try (Connection connection = PostgresSqlConnection.getConnection()) {
            String sql = "UPDATE bank_app.accounts\n" +
                    "SET id=2\n" +
                    "WHERE account_number=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            int c = preparedStatement.executeUpdate();

            System.out.println("Account approved");

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public void rejectAccount(int id) throws BankException {
        try (Connection connection = PostgresSqlConnection.getConnection()) {
            String sql = "DELETE FROM bank_app.accounts\n" +
                    "WHERE account_number=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            int c = preparedStatement.executeUpdate();

            System.out.println("rejection approved");

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }

    }

    @Override//complete
    public List<Account> getCustomerAccount(int id) throws BankException {
        List<Account> accountList = new ArrayList<>();
        try (Connection connection = PostgresSqlConnection.getConnection()) {
            String sql = "SELECT account_number, balance, user_id\n" +
                    "FROM bank_app.accounts where user_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Account accounts = new Account();
                accounts.setAccount_number(resultSet.getInt("account_number"));
                accounts.setBalance(resultSet.getInt("balance"));
                accounts.setUser_id(resultSet.getInt("user_id"));
                accountList.add(accounts);
            }
            if (accountList.size() == 0) {
                System.out.println("No accounts exist in DataBase");
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
        return accountList;
    }

    @Override
    public List<Transaction> getTransactions() throws BankException {
        List<Transaction> transactionList = new ArrayList<>();
        try (Connection connection = PostgresSqlConnection.getConnection()) {
            String sql = "SELECT amount, user_id, account_number, transaction_number\n" +
                    "FROM bank_app.\"transaction\" \n" +
                    "order by account_number;;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Transaction transaction = new Transaction();
                transaction.setAccount_number(resultSet.getInt("account_number"));
                transaction.setAmount(resultSet.getDouble("amount"));
                transaction.setUser(resultSet.getInt("user_id"));
                transactionList.add(transaction);
            }
            if (transactionList.size() == 0) {
                System.out.println("No transactions exist in DataBase");
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
        return transactionList;
    }

    @Override
    public void createAccount(double balance, int user_id) throws BankException {
        Account account = null;
        Random rand = new Random();
        try (Connection connection = PostgresSqlConnection.getConnection()) {
            String sql = "INSERT INTO bank_app.accounts\n" +
                    "(id, account_number, balance, user_id)\n" +
                    "VALUES(1, ?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, rand.nextInt(1001));
            preparedStatement.setDouble(2, balance);
            preparedStatement.setInt(3, user_id);

            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Account has been created");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }

    }

    @Override
    public Account getAccount(int account_number) throws BankException {
        Account account = null;
        try (Connection connection = PostgresSqlConnection.getConnection()) {
            String sql = "SELECT account_number, balance, user_id FROM bank_app.accounts where account_number = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, account_number);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                account = new Account();
                account.setAccount_number(resultSet.getInt("account_number"));
                account.setBalance(resultSet.getDouble("balance"));
                account.setUser_id(resultSet.getInt("user_id"));
            }
            if (account == null) {
                System.out.println("No account with that account number exist in DataBase");
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
        return account;
    }

    @Override
    public void withdrawalFromAccount(double amount, int account_number, int user_id) throws BankException {
        try (Connection connection = PostgresSqlConnection.getConnection()) {
            Account account = null;
            String sql = "SELECT account_number, balance, user_id FROM bank_app.accounts where account_number = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, account_number);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                account = new Account();
                account.setAccount_number(resultSet.getInt("account_number"));
                account.setBalance(resultSet.getDouble("balance"));
                account.setUser_id(resultSet.getInt("user_id"));
            }
            double balance = account.getBalance() - amount;
            sql = "UPDATE bank_app.accounts\n" +
                        "SET balance=?\n" +
                        "WHERE account_number=?;";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setDouble(1, balance);
                preparedStatement.setInt(2, account.getAccount_number());

                int c = preparedStatement.executeUpdate();

                System.out.println("Account approved");

            sql = "INSERT INTO bank_app.\"transaction\"\n" +
                    "(amount, user_id, account_number)\n" +
                    "VALUES(?, ?, ?);";
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setDouble(1, amount - (amount * 2));
            preparedStatement.setInt(2, user_id);
            preparedStatement.setInt(3, account_number);

            c = preparedStatement.executeUpdate();
            if (c == 1) {
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    System.out.println("Transaction has been made");
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    @Override
    public void depositToAccount(double amount, int account_number, int user_id) throws BankException {
        try (Connection connection = PostgresSqlConnection.getConnection()) {
            Account account = null;
            String sql = "SELECT account_number, balance, user_id FROM bank_app.accounts where account_number = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, account_number);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                account = new Account();
                account.setAccount_number(resultSet.getInt("account_number"));
                account.setBalance(resultSet.getDouble("balance"));
                account.setUser_id(resultSet.getInt("user_id"));
            }
            double balance = account.getBalance() + amount;
            sql = "UPDATE bank_app.accounts\n" +
                    "SET balance=?\n" +
                    "WHERE account_number=?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, balance);
            preparedStatement.setInt(2, account.getAccount_number());

            int c = preparedStatement.executeUpdate();


            sql = "INSERT INTO bank_app.\"transaction\"\n" +
                    "(amount, user_id, account_number)\n" +
                    "VALUES(?, ?, ?);";
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setDouble(1, amount);
            preparedStatement.setInt(2, user_id);
            preparedStatement.setInt(3, account_number);

            c = preparedStatement.executeUpdate();
            if (c == 1) {
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    System.out.println("Transaction has been made");
                }
            }

        } catch (SQLException | ClassNotFoundException e) {

        }

    }

    @Override
    public void postMoney(double amount, int account_number1, int account_number2) throws BankException {
        try (Connection connection = PostgresSqlConnection.getConnection()) {
            Account account1 = null;
            Account account2 = null;
            String sql = "SELECT account_number, balance, user_id FROM bank_app.accounts where account_number = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, account_number1);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                account1 = new Account();
                account1.setAccount_number(resultSet.getInt("account_number"));
                account1.setBalance(resultSet.getDouble("balance"));
                account1.setUser_id(resultSet.getInt("user_id"));
            }


            double balance1 = account1.getBalance() - amount;
            sql = "UPDATE bank_app.accounts\n" +
                    "SET balance=?\n" +
                    "WHERE account_number=?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, balance1);
            preparedStatement.setInt(2, account1.getAccount_number());

            int c = preparedStatement.executeUpdate();


            sql = "INSERT INTO bank_app.\"transaction\"\n" +
                    "(amount, user_id, account_number)\n" +
                    "VALUES(?, ?, ?);";
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setDouble(1, amount-(amount*2));
            preparedStatement.setInt(2, account1.getUser_id());
            preparedStatement.setInt(3, account1.getAccount_number());

            c = preparedStatement.executeUpdate();
            if (c == 1) {
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    System.out.println("Transaction has been made");
                }
            }
            sql = "SELECT account_number, balance, user_id FROM bank_app.accounts where account_number = ?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, account_number2);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                account2 = new Account();
                account2.setAccount_number(resultSet.getInt("account_number"));
                account2.setBalance(resultSet.getDouble("balance"));
                account2.setUser_id(resultSet.getInt("user_id"));
            }

            double balance2 = account2.getBalance() + amount;
            sql = "UPDATE bank_app.accounts\n" +
                    "SET balance=?\n" +
                    "WHERE account_number=?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, balance2);
            preparedStatement.setInt(2, account2.getAccount_number());

            c = preparedStatement.executeUpdate();

            sql = "INSERT INTO bank_app.\"transaction\"\n" +
                    "(amount, user_id, account_number)\n" +
                    "VALUES(?, ?, ?);";
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setDouble(1, amount);
            preparedStatement.setInt(2, account2.getUser_id());
            preparedStatement.setInt(3, account2.getAccount_number());

            c = preparedStatement.executeUpdate();
            if (c == 1) {
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    System.out.println("Transaction has been made");
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void acceptMoney(double amount, int account_number1, int account_number2) throws BankException {
        try (Connection connection = PostgresSqlConnection.getConnection()) {
            Account account1 = null;
            Account account2 = null;
            String sql = "SELECT account_number, balance, user_id FROM bank_app.accounts where account_number = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, account_number1);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                account1 = new Account();
                account1.setAccount_number(resultSet.getInt("account_number"));
                account1.setBalance(resultSet.getDouble("balance"));
                account1.setUser_id(resultSet.getInt("user_id"));
            }
            sql = "SELECT account_number, balance, user_id FROM bank_app.accounts where account_number = ?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, account_number2);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                account2 = new Account();
                account2.setAccount_number(resultSet.getInt("account_number"));
                account2.setBalance(resultSet.getDouble("balance"));
                account2.setUser_id(resultSet.getInt("user_id"));
            }

            double balance2 = account2.getBalance() - amount;
            sql = "UPDATE bank_app.accounts\n" +
                    "SET balance=?\n" +
                    "WHERE account_number=?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, balance2);
            preparedStatement.setInt(2, account2.getAccount_number());

            int c = preparedStatement.executeUpdate();

            sql = "INSERT INTO bank_app.\"transaction\"\n" +
                    "(amount, user_id, account_number)\n" +
                    "VALUES(?, ?, ?);";
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setDouble(1, amount-(amount*2));
            preparedStatement.setInt(2, account2.getUser_id());
            preparedStatement.setInt(3, account2.getAccount_number());

            c = preparedStatement.executeUpdate();
            if (c == 1) {
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    System.out.println("Transaction has been made");
                }
            }
            double balance1 = account1.getBalance() + amount;
            sql = "UPDATE bank_app.accounts\n" +
                    "SET balance=?\n" +
                    "WHERE account_number=?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, balance1);
            preparedStatement.setInt(2, account1.getAccount_number());

            c = preparedStatement.executeUpdate();

            sql = "INSERT INTO bank_app.\"transaction\"\n" +
                    "(amount, user_id, account_number)\n" +
                    "VALUES(?, ?, ?);";
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setDouble(1, amount);
            preparedStatement.setInt(2, account1.getUser_id());
            preparedStatement.setInt(3, account1.getAccount_number());

            c = preparedStatement.executeUpdate();
            if (c == 1) {
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    System.out.println("Transaction has been made");
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
