$(function() {
    //hide first div or remove after append using `$(".card:first").remove()`
    $(".card:first").hide()
    $.ajax({
      url: "listFriends",
      success: function(result) {
        $.each(result, function(index, item) {
          var id = item.id;
          var username = item.username;
          var email = item.email;
          document.getElementById('nomeUtente').setAttribute("href","profile.html?id="+id);
          document.getElementById('rimuoviAmico').setAttribute("href","deletefriend?friendId="+id);
          var cards = $(".card:first").clone() //clone first divs
          //add values inside divs
          //$(cards).find(".card-header").html(name);
          $(cards).find(".card-title").html(username);
          $(cards).find(".card-text").html(email);
          $(cards).show() //show cards
          $(cards).appendTo($("#listaAmici")) //append to container
        });
      }
    });
  });