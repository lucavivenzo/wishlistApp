let url = new URL(window.location.href);
let pageId = url.searchParams.get("id");
var eventAddress;
var eventGuests;
var nOfFriendsNotInvited=0;


$(function() {
    //hide first div or remove after append using `$(".card:first").remove()`
    $(".card:first").hide()
    $.ajax({
      url: "event/"+pageId,
      async: false,
      success: function(item) {
        var id = item.id;
        var name = item.name;
        var description = item.description;
        var eventDate = item.date;
        eventAddress = item.eventAddress;
        eventGuests = item.guests;
        //var organizerName = item.organizer.username;//se servirà
        //var guests = item.guests;//se servirà (da iterare)
        document.getElementById('nomeEvent').setAttribute("href","event.html?id="+id);
        var cards = $(".card:first").clone() //clone first divs
        //add values inside divs
        //$(cards).find(".card-header").html(name);
        $(cards).find(".card-header").html("<p class='p-0 m-0 flex-grow-1'>"+name+"</p><button class='btn btn-danger' type='button' onclick='removeEvent()'>Elimina evento</button>");
        $(cards).find(".card-text").html("<p class='p-0 m-0 flex-grow-1'>"+description+' '+eventDate+' '+eventAddress+"</p><button class='btn btn-primary' data-bs-toggle='modal' data-bs-target='#exampleModal'>Aggiungi invitati</button>");
        $(cards).find(".list-group").append("<a class='font-weight-bold' id='refWishlist' href=''></a>Invitati:");
        $.each(eventGuests, function(index2, item2){
          $(cards).find(".list-group").append("<li class='list-group-item'>"+item2.username+"</li>");
        })
        $(cards).show() //show cards
        $(cards).appendTo($("#listaEvents")) //append to container
      }
    });

    $.ajax({
              url: "event/wishlistfromevent",
              async: false,
              data: {idEvent: pageId},
              success: function(item) {
              document.getElementById('refWishlist').innerText+=item.name;
              document.getElementById('refWishlist').setAttribute("href","wishlist.html?id="+item.id);
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

    console.log(eventGuests);
    //prende tutti gli amici che non sono già invitati all'evento e li aggiunge alla schermata di aggiunta invitati
    $.ajax({
      url: "listFriends",
      success: function(result) {
        $.each(result, function(index, item) {
          var id = item.id;
          var username = item.username;
//          console.log(result)
//          console.log(item)
//          console.log(eventGuests.includes(item))

          var contains = eventGuests.some(elem =>{
            return JSON.stringify(item) === JSON.stringify(elem);
          });
          console.log(index)
          if(!(contains)){//aggiungi check con username solo se già non invitati. hanno id crescenti
            document.getElementById('checklistAmici').innerHTML+="<div class='form-check'><input class='form-check-input' type='checkbox' value='"+id+"' id='check"+nOfFriendsNotInvited+"'><label class='form-check-label' for='check"+nOfFriendsNotInvited+"'>"+username+"</label></div>";
            nOfFriendsNotInvited++;
          }
        });
      }
    });

  });

  function removeEvent(){
    $.ajax({
      url: 'event/delete/'+pageId,
      type: 'GET',
      success: function(result, textStatus, errorThrown) {
          if(textStatus=='success'){
            window.location.replace('myEvents.html');
          }
          else {
              alert("Eliminazione fallita. Riprovare.")
          }
      },
      error: function(){alert("Eliminazione fallita. Riprovare.")}
  });
  }

  function inviteFriends(){
    //questa parte crea un array contenente gli id degli amici selezionati da invitare
    var newGuestsIds=new Array();
    var checkElement;
    for(let i=0;i<nOfFriendsNotInvited;i++){
      checkElement=document.getElementById('check'+i)
      console.log(checkElement)
      if(checkElement.checked) {newGuestsIds.push(checkElement.value);}
    }

    console.log(newGuestsIds)

    $.ajax({
      url: '/event/invite',
      type: 'POST',
      headers: {"content-type":"application/json"},
      data: JSON.stringify({idFriend:newGuestsIds, idEvent:pageId}),
      dataType: 'text',
      async: false,
      success: function(result, textStatus, errorThrown) {//result è il token
          if(textStatus=='success'){
              alert("Inviti effettuati.");
              window.location.reload();
          }
          else {
              alert("Operazione fallita. Riprovare.")
          }
      },
      error: function(){alert("Operazione fallita. Riprovare.")}
  });

  }
