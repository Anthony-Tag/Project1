let user
let accounts

function load() {
    let url = "http://localhost:9000/login/" + "jpeters" + "/" + "Password1";
    fetch(url)
        .then(res => res.json())
        .then(res1 => {
            console.log(res1);
            user = res1;
            document.getElementById("welcome").innerHTML = "Welcome " + user.type;
            if (user.type == "customer") {
                getAccountById();
                document.getElementById("type").innerHTML = 
                "<Label>Enter Account number to see an account</label>"+
                "<input type=\"text\" id=\"account_id\" name=\"account_id\">"+
                "<input type =\"button\" onclick=\"getAccountByUserId()\" id=\"forAccount\" value=\"For Account\">"+
                "<br>"+
            "<label>Create account</label>"+
            "<input type=\"button\" onclick=\"createMenu()\" id=\"create\" value=\"Open Menu\">"+
            "<p class=\"error\" id=\"error\"></p>"+
            "<label>Withdraw from amount</label>"+
            "<input type=\"text\" id=\"Wamount\" name=\"Wamount\">"+
            "<label>Withdraw from account</label>"+
            "<input type=\"text\" id=\"Waccount\" name=\"Waccount\">"+
            "<input type=\"button\" onclick=\"withdrawl()\" id=\"withdrawl\" value=\"Withdrawl\">"+
            "<br>"+
            "<label>Deposit to amount</label>"+
            "<input type=\"text\" id=\"Damount\" name=\"Damount\">"+
            "<label> Deposit to account</label>"+
            "<input type=\"text\" id=\"Daccount\" name=\"Daccount\">"+
            "<input type=\"button\" onclick=\"deposit()\" id=\"Deposit\" value=\"Deposit\">"+
            "<br>"+
            "<input type=\"button\" onclick=\"postMenu()\" value=\"Post Menu\">"+
            "<input type=\"button\" onclick=\"acceptMenu()\" value=\"Accept Menu\"></input>"
            } else {

            }
        });

}
function reset() {
    document.getElementById("put").innerHTML = "";
}

function getAccountByUserId() {
    let id = document.getElementById("account_id").value
    console.log(id)
    let url = "http://localhost:9000/account/" + id;
    fetch(url)
        .then(res => res.json())
        .then(res1 => {
            console.log(res1)
            let data = "<table class='table table-bordered table-striped'><thead class='thead-dark'><tr><th>Account number</th><th>Balance</th></tr></thead>";
            data = data + "<tr><td>" + res1.account_number + "</td><td>" + res1.balance + "</td><tr></table>";
            document.getElementById("put").innerHTML = data;
        });
}

function createMenu() {
    let data = "<p class=\"error\" id=\"error\"></p>" +
        "<label>Balance over $50</label>" +
        "<input type=\"text\" id=\"balance\">" +
        "<br><input type=\"button\" onclick=\"createAccount()\" value=\"Create Account\">";
    document.getElementById("put").innerHTML = data;
}

function createAccount() {
    let user_id = user.id;
    let balance = document.getElementById("balance").value
    if (balance == "" || balance < 50) {
        console.log("fail")
        document.getElementById("error").innerHTML = "<p class=\"error\">balance is below 50</p>";
    } else {
        let url = "http://localhost:9000/createAccount";
        fetch(url, {
            method: 'POST',
            body: JSON.stringify({
                "user_id": user_id,
                "balance": balance
            }),
            headers: {
                'Content-type': 'application/json; charset=UTF-8',
            },
        })
            .then(res => res.json())
            .then(res1 => {
                console.log(res1)
            })
    }
}

function withdrawl(){
    let amount = document.getElementById("Wamount").value
    let account = document.getElementById("Waccount").value
    console.log(amount+account+user.id);
    if (amount ==""|| amount <0 || account == "" || account <0){
        document.getElementsByClassName(error).innerHTML = "something is wrong with amount or account number";
    }else{
        let url = "http://localhost:9000/withdrawal/" + amount +"/"+account+"/"+user.id;
    fetch(url)
        .then(res => {
            console.log(res)
            document.getElementById("put").innerHTML = res;
        })
            
    }
}

function deposit(){
    let amount = document.getElementById("Damount").value
    let account = document.getElementById("Daccount").value
    console.log(amount+account+user.id);
    if (amount ==""|| amount <0 || account == "" || account <0){
        document.getElementsByClassName(error).innerHTML = "something is wrong with amount or account number";
    }else{
        let url = "http://localhost:9000/deposit/" + amount +"/"+account+"/"+user.id;
    fetch(url)
        .then(res => {
            console.log(res)
            document.getElementById("put").innerHTML = res;
        })
            
    }
}

function postMenu(){
    document.getElementById("put").innerHTML =
    "<label>Amount</label>"+
            "<input type=\"text\" id=\"Pamount\">"+
            "<label>Account you want to take from</label>"+
            "<input type=\"text\" id=\"account1\">"+
            "<label>Account you want to add to</label>"+
            "<input type=\"text\" id=\"account2\">"+
            "<input type=\"button\" onclick=\"post()\" id =\"post\" value=\"post\"></input>";
}
function post(){
    let amount = document.getElementById("Pamount").value
    let account1 = document.getElementById("account1").value
    let account2 = document.getElementById("account2").value
    console.log(amount+account+user.id);
    if (amount ==""|| amount <0 || account1 == "" || account1 <0 || account2 == "" || account2 <0){
        document.getElementsByClassName(error).innerHTML = "something is wrong with amount or account number";
    }else{
        let url = "http://localhost:9000/post/" + amount +"/"+account1+"/"+account2;
    fetch(url)
        .then(res => {
            console.log(res)
        })
            
    }
}
function acceptMenu(){
    document.getElementById("put").innerHTML =
    "<label>Amount</label>"+
            "<input type=\"text\" id=\"Pamount\">"+
            "<label>Account you want to add to</label>"+
            "<input type=\"text\" id=\"account1\">"+
            "<label>Account you want to take from</label>"+
            "<input type=\"text\" id=\"account2\">"+
            "<input type=\"button\" onclick=\"acceptM()\" id =\"accept\" value=\"accept\"></input>";
}
function acceptM(){
    let amount = document.getElementById("Pamount").value
    let account1 = document.getElementById("account1").value
    let account2 = document.getElementById("account2").value
    console.log(amount+account+user.id);
    if (amount ==""|| amount <0 || account1 == "" || account1 <0 || account2 == "" || account2 <0){
        document.getElementsByClassName(error).innerHTML = "something is wrong with amount or account number";
    }else{
        let url = "http://localhost:9000/accept/" + amount +"/"+account1+"/"+account2;
    fetch(url)
        .then(res => {
            console.log(res)
        })
            
    }
}
function getAccountById() {
    let url = "http://localhost:9000/accounts/" + user.id;
    fetch(url)
        .then(res => res.json())
        .then(res1 => {
            let data = "<lable>Account Numbers</lable><br>"
            console.log(res1)
            accounts = res1;
            res1.forEach(element => {
                data = "<p>" + data + element.account_number + "</p>";
            });
            document.getElementById("account_numbers").innerHTML = data;
        });
}
function arAccount(){
    let url = "http://localhost:9000/accountsARA";
    fetch(url)
    .then(res => res.json())
        .then(res1 => {
            let data = "<lable>Account Numbers</lable><br>"
            console.log(res1)
            accounts = res1;
            res1.forEach(element => {
                data = data +"<p>account number: " + element.account_number + " user_id: "+ element.user_id+" balance: "+element.balance+"</p>"+
                "<input type=\"button\" onclick=\"accept()\" value=\"Accept\">"+
                "<input type=\"button\" onclick=\"reject()\" value=\"Reject\"></input>";
            });
            document.getElementById("put").innerHTML = data;
        });
}
function accept() {
    let id = document.getElementById("accept").value
    let url = "http://localhost:9000/accept/" + id;
    fetch(url)
        .then(res => res.json())
        .then(res1 => {
            console.log(res1);

        });

}

function reject() {
    let id = document.getElementById("reject").value
    let url = "http://localhost:9000/reject/" + id;
    fetch(url)
        .then(res => res.json())
        .then(res1 => {
            console.log(res1);

        });

}
