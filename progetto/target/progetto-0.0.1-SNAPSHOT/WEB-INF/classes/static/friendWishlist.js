let url = new URL(window.location.href);
let pageId = url.searchParams.get("id");

$(function() {
    //hide first div or remove after append using `$(".card:first").remove()`
    $(".card:first").hide()
    $.ajax({
      url: "wishlist/"+pageId+"/friend",
      success: function(result) {
        // $.each(result, function(index, item) {
          var id = result.id;
          var name = result.name;
          var description = result.description;
          var evento = result.event;
          var presents = result.presents;
          var ownerId = result.owner.id;
          var ownerName = result.owner.username;
          document.getElementById('nomeProp').innerText+=' '+ownerName;
          document.getElementById('nomeProprietario').setAttribute("href","profile.html?id="+ownerId);
          var cards = $(".card:first").clone() //clone first divs
          //add values inside divs
          $(cards).find(".card-header").html("Wishlist di "+ownerName);
          $(cards).find(".card-title").html(name);
          $(cards).find(".card-text").html(description);
          $.each(presents, function(index2, item2){
            if(item2.state==false) {$(cards).find(".list-group").append("<li class='list-group-item d-flex'><p class='p-0 m-0 flex-grow-1'>"+"Regalo "+(index2+1)+": "+item2.name+" <br> Descrizione: "+item2.description+" <br> Link: "+"<a href='"+item2.link+"'>"+item2.link+"</a></p><button id ='button"+item2.id+"' type='button' class=' btn btn-danger' onclick='markAsBought("+item2.id+")'>Segna come acquistato</button></li>");}
            else {$(cards).find(".list-group").append("<li class='list-group-item d-flex'><p class='p-0 m-0 flex-grow-1'>"+"Regalo "+(index2+1)+": "+item2.name+" <br> Descrizione: "+item2.description+" <br> Link: "+"<a href='"+item2.link+"'>"+item2.link+"</a></p><button type='button' class=' btn btn-danger' disabled>Già acquistato</button></li>");}
            //$(cards).find(".list-group").append("<li class='list-group-item'>"+"Regalo "+(index2+1)+": "+item2.name+" "+item2.description+" "+item2.link+"</li>");
          })
          $(cards).show() //show cards
          $(cards).appendTo($("#listaWishlists")) //append to container
        // });
      }
    });
  });

  function markAsBought(idRegalo){
    $.ajax({
      url: 'wishlist/buy',
      type: 'GET',
      data: {idPresent:idRegalo},
      success: function(result, textStatus, errorThrown) {
          if(textStatus=='success'){
            $("#button"+idRegalo).text("Già acquistato");
            $("#button"+idRegalo).prop("disabled",true);
          }
          else {
              alert("Operazione fallita. Riprovare.")
          }
      },
      error: function(){alert("Operazione fallita. Riprovare.")}
  });

  }