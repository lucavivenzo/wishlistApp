function logout(){
    $.ajax({
        url: '/logout',
        type: 'GET',
        async: false,
        success: function(result, textStatus, errorThrown) {//result è il token
            if(textStatus=='success'){
                alert("Logout effettuato.")
            }
            else {
                alert("Logout fallito.")
            }
        },
        error: function(){alert("Logout fallito.")}
    });
}