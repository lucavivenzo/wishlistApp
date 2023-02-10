let url = new URL(window.location.href);
let pattern = url.searchParams.get("pattern");

$(function() {
    document.getElementById('risRicerca').innerText+=" '"+pattern+"'";
    //hide first div or remove after append using `$(".card:first").remove()`
    $(".card:first").hide()
    $.ajax({
      url: "search",
      data: {pattern: pattern},
      success: function(result) {
        $.each(result, function(index, item) {
          var id = item.id;
          var username = item.username;
          var email = item.email;
          //TODO: non credo abbia molto senso visualizzare profilo di non amici. Capire quindi cosa fare quando si accede da url al profilo di un non amico
          //document.getElementById('nomeUtente').setAttribute("href","profile/"+id);
          document.getElementById('aggiungi').setAttribute("onclick","addFriend("+id+")");
          var cards = $(".card:first").clone() //clone first divs
          //add values inside divs
          $(cards).find(".card-title").html(username);
          $(cards).find(".card-text").html(email);
          $(cards).show() //show cards
          $(cards).appendTo($("#listaUtenti")) //append to container
        });
      }
    });
  });

  function addFriend(idFriend){
    $.ajax({
      url: "addFriend",
      async: false,
      data: {friendId : idFriend},
      success: function(item, textStatus, errorThrown) {
        if(textStatus=='success'){//qua volendo in base alle stringhe restituite dare diversi errori
          alert(item);
          window.location.reload();
        }
        else {
            alert("Operazione fallita. Riprovare.")
        }
      },
      error: function(){alert("Operazione fallita. Riprovare.")}
    });
  }