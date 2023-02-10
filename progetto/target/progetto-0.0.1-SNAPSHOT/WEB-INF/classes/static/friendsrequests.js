$(function() {
    //hide first div or remove after append using `$(".card:first").remove()`
    $(".card:first").hide()
    $.ajax({
      url: "listPendingRequests",
      success: function(result) {
        $.each(result, function(index, item) {
          var id = item.value0.id;
          var username = item.value0.username;
          var email = item.value0.email;
          var dataRichiesta = item.value1;
          //TODO: non credo abbia molto senso visualizzare profilo di non amici. Capire quindi cosa fare quando si accede da url al profilo di un non amico
          //document.getElementById('nomeUtente').setAttribute("href","profile/"+id);
          document.getElementById('accetta').setAttribute("onclick","accept("+id+")");
          document.getElementById('rifiuta').setAttribute("onclick","refuse("+id+")");
          var cards = $(".card:first").clone() //clone first divs
          //add values inside divs
          $(cards).find(".card-header").html(dataRichiesta);
          $(cards).find(".card-title").html(username);
          $(cards).find(".card-text").html(email);
          $(cards).show() //show cards
          $(cards).appendTo($("#listaAmici")) //append to container
        });
      }
    });
  });

  function accept(idFriend){
    $.ajax({
      url: "setFriendship",
      async: false,
      data: {accept : 1, friendId : idFriend},
      success: function(item, textStatus, errorThrown) {
        if(textStatus=='success'){
          alert("Amico aggiunto con successo");
          window.location.reload();
        }
        else {
            alert("Operazione fallita. Riprovare.")
        }
      },
      error: function(){alert("Operazione fallita. Riprovare.")}
    });
  }

  function refuse(idFriend){
    $.ajax({
      url: "setFriendship",
      async: false,
      data: {accept : 0, friendId : idFriend},
      success: function(item, textStatus, errorThrown) {
        if(textStatus=='success'){
          alert("Richiesta di amicizia rifiutata");
          window.location.reload();
        }
        else {
            alert("Operazione fallita. Riprovare.")
        }
      },
      error: function(){alert("Operazione fallita. Riprovare.")}
    });
  }