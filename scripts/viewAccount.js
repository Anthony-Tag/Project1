let user
function load(){
let url = "http://localhost:9000/login/"+"user"+"/"+"pass";
fetch(url)
.then(res => res.json())
        .then(res1 => {
            console.log(res1);
            user = res1;
            document.getElementById("welcome").innerHTML = "Welcome "+user.type;
         });

        }
f1.addEventListener("click", () => {
    let id = document.getElementById("i1").value
    console.log(user)
    console.log(id)
    let url = "http://localhost:9000/account/79";
    fetch(url)
        .then(res => res.json())
        .then(res1 => {
            console.log(res1)
            let data = "<p>";
            document.getElementById("account_number").innerHTML = res1;
        });
})
f2.addEventListener("click", () => {
    let id=document.getElementById("i2").value
    console.log(id)
    let url = "http://localhost:9000/accounts/"+id;
    fetch(url)
        .then(res => res.json())
        .then(res1 => {
            let data = "<p>"
            console.log(res1)
            res1.forEach(element => {
                data = data+element.account_number+"</p>";
            });
            document.getElementById("account_numbers").innerHTML = data;
        });
})