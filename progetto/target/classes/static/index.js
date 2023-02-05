$(function() {
    //hide first div or remove after append using `$(".card:first").remove()`
    $(".card:first").hide()
    $.ajax({
      url: "wishlist/friendswishlist",
      success: function(result) {
        $.each(result, function(index, item) {
          var id = item.id;
          var name = item.name;
          var description = item.description;
          var evento = item.event;
          var presents = item.presents;
          var ownerId = item.owner.id;
          var ownerName = item.owner.username;
          document.getElementById('nomeProprietario').setAttribute("href","profile.html?id="+ownerId);
          document.getElementById('nomeWishlist').setAttribute("href","wishlist.html?id="+id);
          var cards = $(".card:first").clone() //clone first divs
          //add values inside divs
          $(cards).find(".card-header").html("Wishlist di "+ownerName);
          $(cards).find(".card-title").html(name);
          $(cards).find(".card-text").html(description);
          $.each(presents, function(index2, item2){
              $(cards).find(".list-group").append("<li class='list-group-item'>"+item2.name+" "+item2.description+" "+item2.link+"</li>");
          })
          $(cards).show() //show cards
          $(cards).appendTo($("#listaWishlists")) //append to container
        });
      }
    });
  });