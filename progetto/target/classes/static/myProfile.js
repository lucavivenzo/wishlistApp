$(function() {
    $(".card:first").hide()
    $.ajax({
      url: "wishlist/all",
      success: function(result) {
        var nomeUtente=result[0].owner.username;
        var emailUtente=result[0].owner.email;
        document.getElementById('nomeProfilo').innerText=nomeUtente;
        document.getElementById('emailProfilo').innerText=emailUtente;
        $.each(result, function(index, item) {
          var id = item.id;
          var name = item.name;
          var description = item.description;
          var evento = item.event;
          var presents = item.presents;
          document.getElementById('nomeWishlist').setAttribute("href","wishlist.html?id="+id);
          var cards = $("#cardWishlist").clone() //clone first divs
          //add values inside divs
          //$(cards).find(".card-header").html(name);
          $(cards).find(".card-title").html(name);
          $(cards).find(".card-text").html(description);
          $.each(presents, function(index2, item2){
              $(cards).find(".list-group").append("<li class='list-group-item'>"+"Regalo "+(index2+1)+": "+item2.name+" "+item2.description+" "+item2.link+"</li>");
          })
          $(cards).show() //show cards
          $(cards).appendTo($("#listaWishlists")) //append to container
        });
      }
    });
  });