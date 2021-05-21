# Revature Bank App

## Project Description:

The project Revature Bank App is an application that has a frontend made from HTML/CSS and JavaScript to call to end points. Backend in Java using Javalin to communicate with JavaScript and JDBC to connect to a RDS postgreSQL database. In the Bank app you will either enter as a customer and have the ability to withdraw, deposit, lookup transactions on each account, create account, post money, and accept money from another account. In employee you will be able to look up bank accounts by user id, transactions by user id, transition number, and account number. There is an accept and reject option that does not work at the moment.

## Technologies Used

* HTML/CSS/Bootstrap - HTML 5
* JavaScript - ECMAScript 2018
* Java - 8

## Features

List of features ready and TODOs for future development
* Create a new customer or employee in the DB
* Get account transactions from current accounts 
* Get account details
* Withdraw and deposit mone in customer account by updating DB 
* Delete accounts pending approval from the account table

To-do list:
* Finish CSS to make the HTML look more presentable
* Fix accept and reject commands in employee page

## Getting Started
   
git remote add origin https://github.com/Anthony-Tag/Revature_Bank_App.git

> Compile and run the java code for the server and DB connection  
> Open up the Index.html in the browser and pick a login

- run in a IDE or Gradle 

## Usage

> You open index.htmnl to get a screen that lets you have three options login as customer, login as employee, and create user. From there you will login correctly (user, pass for customer, jpeters, Password1 for employee) and login again at the next page. You will be about to customer things like withdraw and deposit from the customer account and more admin things like look up accounts and transactions of accounts (user_id 8, account_number 1254 1234, transaction 18).
