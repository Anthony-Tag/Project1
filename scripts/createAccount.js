function submitForm(){
    let username=document.getElementById("username").value
    let password=document.getElementById("password").value
    let type=document.getElementById("type").value
    if (username == "" || password == ""){
        document.getElementById("error").innerHTML = "<p>username or password are missing</p>";
        console.log("error");
    }else {
    console.log(username+" "+password)
    let url = "http://localhost:9000/createUser";
            fetch(url, {
                method: 'POST',
                body: JSON.stringify({
                    "username": username,
                    "password": password,
                    "type": type
                }),
                headers: {
                    'Content-type': 'application/json; charset=UTF-8',
                },
            })
        .then(res => res.json())
        .then(res1 => {
            console.log(res1)
                console.log("enter");
                document.getElementById("form").submit();
         });
        }
    }