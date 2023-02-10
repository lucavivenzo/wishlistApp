$(function() {

    $(".card:first").hide()
    $.ajax({
    url: "event/myevents",
    success: function(result) {
      $.each(result, function(index, item) {
        var id = item.id;
        var name = item.name;
        var description = item.description;
        var eventDate = item.date;
        var eventAddress = item.eventAddress;
        document.getElementById('nomeEvent').setAttribute("href","event.html?id="+id);
        var cards = $(".card:first").clone() //clone first divs
        //add values inside divs
        //$(cards).find(".card-header").html(name);
        $(cards).find(".card-title").html(name);
        $(cards).find(".card-text").html(description+' '+eventDate+' '+eventAddress);
        
        $(cards).show() //show cards
        $(cards).appendTo($("#listaEvents")) //append to container
      });
    }
  });

    $.ajax({//fa uscire nel menu a tendina solo le tue wishlist non associate già ad un evento
      url: "wishlist/all",
      success: function(result) {
        $.each(result, function(index, item) {
          var id = item.id;
          var name = item.name;
          var description = item.description;
          var evento = item.event;
          if(evento==null){
            $(".form-select").append("<option value='"+id+"'>"+name+"</option>");
          }
        });
      }
    });
  });

  function addEvent(){
    let nomeE = document.getElementById('inputNome')
    let descrizioneE = document.getElementById('inputDescrizione')
    let dataE = document.getElementById('inputData')
    let indirizzoE = document.getElementById('inputIndirizzo')
    let checkE = document.getElementById('wishlistCheck')
    let wishlistE = document.getElementById('inputWishlist')
    
    console.log(nomeE.value)
    console.log(descrizioneE.value)
    console.log(dataE.value)
    console.log(indirizzoE.value)
    console.log(checkE.checked)
    console.log(wishlistE.value)

    if(!nomeE.value) {window.alert("Inserire il nome dell'evento"); return}
    if(!descrizioneE.value) {window.alert("Inserire una descrizione dell'evento"); return}
    if(!dataE.value) {window.alert("Inserire una data dell'evento"); return}
    if(!indirizzoE.value) {window.alert("Inserire un indirizzo dell'evento"); return}
    if(checkE.checked && wishlistE.value==0) {window.alert("Selezionare una wishlist"); return}
    if(checkE.checked) {
        $.ajax({
            url: 'event/create',
            type: 'GET',
            data: {name:nomeE.value, description:descrizioneE.value, date:dataE.value, eventAddress:indirizzoE.value, id:wishlistE.value},
            success: function(result, textStatus, errorThrown) {
                if(textStatus=='success'){
                    alert('Inserimento riuscito.')
                    window.location.replace('event.html?id='+result.id);
                }
                else {
                    alert("Inserimento fallito. Riprovare.")
                }
            },
            error: function(){alert("Inserimento fallito. Riprovare.")}
        });
    }
    else if (!checkE.checked){
        $.ajax({
            url: 'event/create',
            type: 'GET',
            data: {name:nomeE.value, description:descrizioneE.value, date:dataE.value, eventAddress:indirizzoE.value},
            success: function(result, textStatus, errorThrown) {
                if(textStatus=='success'){
                    alert('Inserimento riuscito.')
                    window.location.replace('event.html?id='+result.id);
                }
                else {
                    alert("Inserimento fallito. Riprovare.")
                }
            },
            error: function(){alert("Inserimento fallito. Riprovare.")}
        });
    }
    
}