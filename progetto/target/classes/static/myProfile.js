$(function() {
  $("#cardWishlist").hide()
  $("#cardEvent").hide()
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

  $.ajax({
    url: "event/myevents",
    success: function(result) {
      $.each(result, function(index, item) {
        var eventId = item.id;
        var eventName = item.name;
        var eventDescription = item.description;
        var eventDate = item.date;
        var eventAddress = item.eventAddress;
        document.getElementById('nomeEvent').setAttribute("href","event.html?id="+eventId);
        var cards = $("#cardEvent").clone() //clone first divs
        //add values inside divs
        //$(cards).find(".card-header").html(name);
        $(cards).find(".card-title").html(eventName);
        $(cards).find(".card-text").html(eventDescription+' '+eventDate+' '+eventAddress);
        
        $(cards).show() //show cards
        $(cards).appendTo($("#listaEvents")) //append to container
      });
    }
  });



});