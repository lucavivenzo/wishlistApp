$(function() {
  //hide first div or remove after append using `$(".card:first").remove()`
  $(".card:first").hide()
  $.ajax({
    url: "wishlist/all",
    success: function(result) {
      $.each(result, function(index, item) {
        var id = item.id;
        var name = item.name;
        var description = item.description;
        var evento = item.event;
        var presents = item.presents;
        document.getElementById('nomeWishlist').setAttribute("href","wishlist.html?id="+id);
        var cards = $(".card:first").clone() //clone first divs
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
});

function addWishlist(){
  let nomeW = document.getElementById('inputNome')
  let descrizioneW = document.getElementById('inputDescrizione')
  if(!nomeW.value) {window.alert('Inserire il nome della wishlist'); return}

  $.ajax({
    url: 'wishlist/create',
    type: 'GET',
    data: {name:nomeW.value, description:descrizioneW.value},
    success: function(result, textStatus, errorThrown) {
        if(textStatus=='success'){
          alert('Inserimento riuscito.')
          window.location.replace('wishlist.html?id='+result.id);
        }
        else {
            alert("Inserimento fallito. Riprovare.")
        }
    },
    error: function(){alert("Inserimento fallito. Riprovare.")}
  });
}