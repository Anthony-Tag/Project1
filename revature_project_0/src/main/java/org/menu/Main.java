package org.menu;

import io.javalin.Javalin;
import org.apache.log4j.Logger;
import org.menu.exception.BankException;
import org.menu.model.Account;
import org.menu.model.Empty;
import org.menu.model.Transaction;
import org.menu.model.User;
import org.menu.service.BankAppSearch;
import org.menu.service.impl.BankAppSearchImpl;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws BankException {

        Logger log = Logger.getLogger(Main.class);
        Scanner scanner = new Scanner(System.in);
        BankAppSearch bankAppSearch = (BankAppSearch) new BankAppSearchImpl();
        Javalin app = Javalin.create(config->config.enableCorsForAllOrigins()).start(9000);

        app.get("/login/:username/:password",ctx -> {//Get user for login
            User user=bankAppSearch.getUser((ctx.pathParam("username")),(ctx.pathParam("password")));
            if(user == null){
                Empty empty=new Empty(0);
                ctx.json(empty);
            }else {
                ctx.json(user);
            }
        });

        app.post("/createUser",ctx -> {//Create user in DB
            User user=ctx.bodyAsClass(User.class);
            //user=bankAppSearch.createUser(user.getUsername(),user.getPassword(), user.getType());
            if(user == null){
                Empty empty=new Empty(0);
                ctx.json(empty);
            }else {
                ctx.json(user);
            }

        });
        app.get("/account/:account_id",ctx -> {//Get one user account
            Account account=bankAppSearch.getAccount(Integer.parseInt(ctx.pathParam("account_id")));
            ctx.json(account);
        });
        app.get("/accounts/:id",ctx -> {//Get one user accounts
            List<Account> accounts=bankAppSearch.getCustomerAccount(Integer.parseInt(ctx.pathParam("id")));
            ctx.json(accounts);
        });
        app.get("/accountsARA",ctx -> {//Get one user accounts
            List<Account> accounts=bankAppSearch.getARAccount();
            ctx.json(accounts);
        });
        app.get("/accounts/accept/:account_id",ctx -> {//Get one user accounts
            bankAppSearch.approveAccount(Integer.parseInt(ctx.pathParam("account_id")));
            ctx.result("finished");
        });
        app.get("/accounts/reject/:account_id",ctx -> {//Get one user accounts
            bankAppSearch.rejectAccount(Integer.parseInt(ctx.pathParam("account_id")));
            ctx.result("finished");
        });
//                        log.info("3) View a log of transactions from all account");
//                        log.info("4) Exit back");
//                        try {
//                            ch = Integer.parseInt(scanner.nextLine());
//                        } catch (NumberFormatException e) {
//                            break;
//                        }
//                        switch (ch) {
//                            case 1://approve and reject
//                                try {
//                                    List<Account> accountList = bankAppSearch.getARAccount();
//                                    log.info("Finished");
//                                } catch (BankException e) {
//                                    log.warn(e);
//                                }
//                                break;
//                            case 2://view bank account
//                                log.info("Enter customer id");
//                                try {
//                                    int id = Integer.parseInt(scanner.nextLine());
//                                    List<Account> accounts = bankAppSearch.getCustomerAccount(id);
//                                    if (accounts != null) {
//                                        log.info("Accounts found");
//                                        log.info(accounts);
//                                    }
//                                } catch (NumberFormatException e) {
//                                    log.warn("Enter integer only");
//                                }
//                                break;
//                            case 3://view log of all transcript
//                                try {
//                                    List<Transaction> transactionList = bankAppSearch.getTransactions();
//                                    if (transactionList != null) {
//                                        log.info("Accounts found");
//                                        log.info(transactionList);
//                                    }
//                                } catch (BankException e) {
//                                    log.warn("Account not found");
//                                }
//                                break;
//                            case 4://exit
//                                break;
//                            default:
//                                log.warn("Incorrect response");
//                        }
//                    } while (ch != 4);
//                } else {//customer
//                    do {
//                        log.info("Welcome Customer:");
//                        log.info("1) Apply for a new bank account");
//                        log.info("2) View balance of one account");
//                        log.info("3) Withdrawal or deposit in an account");
//                        log.info("4) Post money transfer to an other account");
//                        log.info("5) Accept money transfer from an other account");
//                        log.info("6) Exit");
//                        try {
//                            ch = Integer.parseInt(scanner.nextLine());
//                        } catch (NumberFormatException e) {
//
//                        }
//                        switch (ch) {
//                            case 1://new account
//                                try {
//                                    log.info("Enter base balance greater than $50 for your account");
//                                    double amount = Double.parseDouble(scanner.nextLine());
//                                    bankAppSearch.createAccount(amount, user.getId());
//                                } catch (BankException e) {
//                                    log.warn("Something went wrong please try again");
//                                }
//                                break;
//                            case 2://view balance
//                                try {
//                                    List<Account> accountList = bankAppSearch.getCustomerAccount(user.getId());
//                                    log.info("Your accounts:");
//                                    for (int i = 0; i < accountList.size(); i++) {
//                                        log.info("#" + accountList.get(i).getAccount_number());
//                                    }
//                                    log.info("Enter the account number that you want to see the balance of: ");
//                                    int account_number = Integer.parseInt(scanner.nextLine());
//                                    Account account = bankAppSearch.getAccount(account_number);
//                                    log.info(account.getBalance());
//                                } catch (BankException e) {
//                                    log.warn("Something went wrong");
//                                }
//                                break;
//                            case 3://withdrawal or deposit
//                                try {
//                                    List<Account> accounts = bankAppSearch.getCustomerAccount(user.getId());
//
//                                    log.info(accounts);
//                                    log.info("Would you like to withdrawal or deposit");
//                                    String ans = scanner.nextLine();
//                                    if (ans.equalsIgnoreCase("withdrawal")) {
//                                        log.info("Enter the amount you would like to withdrawal");
//                                        double amt = Double.parseDouble(scanner.nextLine());
//                                        log.info("Enter the account number you would like to take from");
//                                        int acc_num = Integer.parseInt(scanner.nextLine());
//                                        bankAppSearch.withdrawalFromAccount(amt, acc_num, user.getId());
//                                    } else if (ans.equalsIgnoreCase("deposit")) {
//                                        log.info("Enter the amount you would like to deposit");
//                                        double amt = Double.parseDouble(scanner.nextLine());
//                                        log.info("Enter the account number you would like to put in");
//                                        int acc_num = Integer.parseInt(scanner.nextLine());
//                                        bankAppSearch.withdrawalFromAccount(amt, acc_num, user.getId());
//                                    }
//                                } catch (BankException e) {
//                                    log.warn("Something went wrong");
//                                }
//                                break;
//                            case 4://post money transfer
//                                try {
//                                    log.info("How much money would you like to post");
//                                    double post = Double.parseDouble(scanner.nextLine());
//                                    log.info("What account number would you like to take from");
//                                    int account1 = Integer.parseInt(scanner.nextLine());
//                                    log.info("what account number would you like to put it in");
//                                    int account2 = Integer.parseInt(scanner.nextLine());
//                                    bankAppSearch.postMoney(post, account1, account2);
//                                } catch (NumberFormatException e) {
//                                    log.warn("Something went wrong");
//                                }
//                                break;
//                            case 5://accept money transfer
//                                try {
//                                    log.info("How much money would you like to accept");
//                                    double post = Double.parseDouble(scanner.nextLine());
//                                    log.info("What account number would you like to put it in");
//                                    int account1 = Integer.parseInt(scanner.nextLine());
//                                    log.info("what account number would you like to accept it from");
//                                    int account2 = Integer.parseInt(scanner.nextLine());
//                                    bankAppSearch.acceptMoney(post, account1, account2);
//                                } catch (NumberFormatException e) {
//                                    log.warn("Something went wrong");
//                                }
//                                break;
//                            case 6://exit
//                                break;
//                            default:
//                                log.warn("Incorrect input");
//                        }
//                    } while (ch != 6);
//                }
//
//            } catch (NullPointerException | BankException e) {
//                break;
//            }
//            log.info("Would you like to Logout of the Bank App (Yes/No)");
//            logE = scanner.nextLine();
//            if (logE.equals("yes") || logE.equals("Yes")) {
//                exit = true;
//            } else {
//                exit = false;
//            }
//
//        }
//        log.info("Thank You for using the app");
    }
}
