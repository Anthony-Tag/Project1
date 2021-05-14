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
            user=bankAppSearch.createUser(user.getUsername(),user.getPassword(), user.getType());
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
        app.post("/createAccount",ctx -> {//Create user in DB
            Account account=ctx.bodyAsClass(Account.class);
            bankAppSearch.createAccount(account.getBalance(),account.getUser_id());
            if(account == null){
                Empty empty=new Empty(0);
                ctx.json(empty);
            }else {
                ctx.json(account);
            }
        });

        app.get("/withdrawal/:amount/:account/:id",ctx -> {//Get one user accounts
            bankAppSearch.withdrawalFromAccount(Double.parseDouble(ctx.pathParam("amount")),Integer.parseInt(ctx.pathParam("account")),Integer.parseInt(ctx.pathParam("id")));
            ctx.result("done");
        });

        app.get("/deposit/:amount/:account/:id",ctx -> {//Get one user accounts
            bankAppSearch.depositToAccount(Double.parseDouble(ctx.pathParam("amount")),Integer.parseInt(ctx.pathParam("account")),Integer.parseInt(ctx.pathParam("id")));
            ctx.result("done");
        });

        app.get("/post/:amount/:account1/:account2",ctx -> {//Get one user accounts
            bankAppSearch.postMoney(Double.parseDouble(ctx.pathParam("amount")),Integer.parseInt(ctx.pathParam("account1")),Integer.parseInt(ctx.pathParam("account2")));
            ctx.result("done");
        });

        app.get("/accept/:amount/:account1/:account2",ctx -> {//Get one user accounts
            bankAppSearch.acceptMoney(Double.parseDouble(ctx.pathParam("amount")),Integer.parseInt(ctx.pathParam("account1")),Integer.parseInt(ctx.pathParam("account2")));
            ctx.result("done");
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

        app.get("/transactions/id/:id",ctx -> {
           List<Transaction> transactionList= bankAppSearch.getTransactionsId(Integer.parseInt(ctx.pathParam("id")));
           ctx.json(transactionList);
        });

        app.get("/transactions/account/:account",ctx -> {
            List<Transaction> transactionList= bankAppSearch.getTransactionsAcc(Integer.parseInt(ctx.pathParam("account")));
            ctx.json(transactionList);
        });
        app.get("/transactions/trans/:trans",ctx -> {
            System.out.println(Integer.parseInt(ctx.pathParam("trans")));
            List<Transaction> transactionList= bankAppSearch.getTransactionsTrans(Integer.parseInt(ctx.pathParam("trans")));
            ctx.json(transactionList);
        });
    }
}
