let emailLogin = document.getElementById('floatingInput')
let passwordLogin = document.getElementById('floatingPassword')

let usernameRegister = document.getElementById('floatingUsername')
let emailRegister = document.getElementById('floatingInputRegister')
let passwordRegister = document.getElementById('floatingPasswordRegister')

function login(){
    if(!emailLogin.value) {window.alert('Inserire una email'); return}
    if(!passwordLogin.value) {window.alert('Inserire una password'); return}
    console.log("login")
    $.ajax({
        url: '/login',
        type: 'POST',
        headers: {"content-type":"application/json"},
        data: JSON.stringify({email:emailLogin.value, password:passwordLogin.value}),
        dataType: 'text',
        async: false,
        success: function(result, textStatus, errorThrown) {//result è il token
            if(textStatus=='success'){
                alert("Accesso effettuato")
                console.log(result)//è il token
                //store token(?)
            }
            else {
                alert("Email o password errate. Riprovare.")
            }
        },
        error: function(){alert("Email o password errate. Riprovare.")}
    });
}