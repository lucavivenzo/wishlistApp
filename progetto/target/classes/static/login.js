let emailLogin = document.getElementById('floatingInput')
let passwordLogin = document.getElementById('floatingPassword')

let usernameRegister = document.getElementById('floatingUsername')
let emailRegister = document.getElementById('floatingInputRegister')
let passwordRegister = document.getElementById('floatingPasswordRegister')

function login(){
    if(!emailLogin.value) {window.alert('Inserire una email'); return}
    if(!passwordLogin.value) {window.alert('Inserire una password'); return}
    $.ajax({
        url: '/login',
        type: 'POST',
        headers: {"content-type":"application/json"},
        data: JSON.stringify({email:emailLogin.value, password:passwordLogin.value}),
        dataType: 'text',
        async: false,
        success: function(result, textStatus, errorThrown) {//result è il token
            if(textStatus=='success'){
                window.location.replace('index.html');
                return false;
            }
            else {
                alert("Email o password errate. Riprovare.")
            }
        },
        error: function(){alert("Email o password errate. Riprovare.")}
    });
}

function register(){
    if(!usernameRegister.value) {window.alert('Inserire un nome utente'); return}
    if(!emailRegister.value) {window.alert('Inserire una email'); return}
    if(!passwordRegister.value) {window.alert('Inserire una password'); return}

    $.ajax({
        url: '/register',
        type: 'POST',
        headers: {"content-type":"application/json"},
        data: JSON.stringify({username:usernameRegister.value, email:emailRegister.value, password:passwordRegister.value}),
        dataType: 'text',
        async: false,
        success: function(result, textStatus, errorThrown) {//result è il token
            if(textStatus=='success'){
                alert("Registrazione effettuata. Effettuare il login.")
            }
            else {
                alert("Registrazione fallita. Riprovare.")
            }
        },
        error: function(){alert("Registrazione fallita. Riprovare.")}
    });
}
