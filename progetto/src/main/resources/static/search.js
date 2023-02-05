let url = new URL(window.location.href);
let pattern = url.searchParams.get("pattern");

$(function() {
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
          document.getElementById('aggiungi').setAttribute("href","addFriend?friendId="+id);
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