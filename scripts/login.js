function submitForm(){
    let username=document.getElementById("username").value
    let password=document.getElementById("password").value
    let type=document.getElementById("type").value
    document.getElementById("error").innerHTML = "";
    if (username == "" || password == ""){
        document.getElementById("error").innerHTML = "<p>username or password is incorrect</p>";
        console.log("error");
    }else {
    console.log(username+" "+password)
    let url = "http://localhost:9000/login/"+username+"/"+password;
    fetch(url)
        .then(res => res.json())
        .then(res1 => {
            console.log(res1.good);
            if (res1.good == 0){
                document.getElementById("error").innerHTML = "<p>username or password is incorrect</p>";
            }else {
                console.log("enter");
                document.getElementById("form").submit();
            }
         });
        }
    }