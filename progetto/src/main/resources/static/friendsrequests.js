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
          document.getElementById('accetta').setAttribute("href","setFriendship?accept=1&friendId="+id);
          document.getElementById('rifiuta').setAttribute("href","setFriendship?accept=0&friendId="+id);
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