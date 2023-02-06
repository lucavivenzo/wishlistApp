let url = new URL(window.location.href);
let eventId = url.searchParams.get("eventId");
let friendId = url.searchParams.get("friendId");
var eventAddress;


$(function() {
    //hide first div or remove after append using `$(".card:first").remove()`
    $(".card:first").hide()
    $.ajax({
      url: "event/invitation",
      async: false,
      data: {idFriend:friendId, idEvent: eventId},
      success: function(item) {
        var id = item.id;
        var name = item.name;
        var description = item.description;
        var eventDate = item.date;
        eventAddress = item.eventAddress;
        var eventGuests = item.guests;
        
        var cards = $(".card:first").clone() //clone first divs
        //add values inside divs
        //$(cards).find(".card-header").html(name);
        $(cards).find(".card-header").html(name);
        $(cards).find(".card-text").html(description+' '+eventDate+' '+eventAddress);
        $.each(eventGuests, function(index2, item2){
          $(cards).find(".list-group").append("<li class='list-group-item'>"+item2.username+"</li>");
        })
        $(cards).show() //show cards
        $(cards).appendTo($("#listaEvents")) //append to container
      }
    });
    
    $.ajax({
      url: "https://nominatim.openstreetmap.org/search?q="+encodeURIComponent(eventAddress)+"&format=json",
      async: false,
      success: function(item){
        console.log(eventAddress)
        var lat=item[0].lat;
        var lon=item[0].lon;
        var w=window.innerWidth;
        document.getElementById('mappaColonna').innerHTML="<iframe width='325' height='280' frameborder='0' src='https://www.bing.com/maps/embed?h=500&w="+w+"&cp="+lat+"~"+lon+"&lvl=17&typ=d&sty=r&src=SHELL&FORM=MBEDV8 scrolling='no'></iframe>"
        //document.getElementById('mappa').setAttribute("src","https://www.bing.com/maps/embed?h=280&w=325&cp="+lat+"~"+lon+"&lvl=2.037847582724944&typ=d&sty=r&src=SHELL&FORM=MBEDV8");
      },
      error: function(){
        console.log("connessione con API openstreetmap fallita");
        document.getElementById('mappaColonna').innerHTML="Impossibile caricare la mappa"
      }
    })


  });
