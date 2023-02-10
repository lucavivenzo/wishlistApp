let url = new URL(window.location.href);
let pageId = url.searchParams.get("id");
var eventAddress;

$(function() {
    $("#cardWishlist").hide()
    $("#cardEvent").hide()

    $.ajax({
      url: 'user',
      type: 'GET',
      data: {id:pageId},
      success: function(result, textStatus, errorThrown) {
        var nomeUtente=result.username;
        var emailUtente=result.email;
        document.getElementById('nomeProfilo').innerText=nomeUtente;
        document.getElementById('emailProfilo').innerText=emailUtente;
      }
    });

    $.ajax({
      url: "wishlist/wishlistsofafriend",
      data: {friendId:pageId},
      success: function(result) {
        $.each(result, function(index, item) {
          var id = item.id;
          var name = item.name;
          var description = item.description;
          var evento = item.event;
          var presents = item.presents;
          document.getElementById('nomeWishlist').setAttribute("href","friendWishlist.html?id="+id);
          var cards = $("#cardWishlist").clone() //clone first divs
          //add values inside divs
          //$(cards).find(".card-header").html(name);
          $(cards).find(".card-title").html(name);
          $(cards).find(".card-text").html(description);
          $.each(presents, function(index2, item2){
              $(cards).find(".list-group").append("<li class='list-group-item'>"+"Regalo "+(index2+1)+": "+item2.name+" <br> Descrizione: "+item2.description+" <br> Link: "+"<a href='"+item2.link+"'>"+item2.link+"</a></li>");
          })
          $(cards).show() //show cards
          $(cards).appendTo($("#listaWishlists")) //append to container
        });
      }
    });
  
    $.ajax({
      url: "event/myinvitationsby/"+pageId,
      success: function(result) {
        $.each(result, function(index, item) {
          var eventId = item.id;
          var eventName = item.name;
          var eventDescription = item.description;
          var eventDate = item.date;
          var eventAddress = item.eventAddress;
          var eventOrganizerId = item.organizer.id;
          document.getElementById('nomeEvent').setAttribute("href","friendEvent.html?eventId="+eventId+"&friendId="+eventOrganizerId);
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