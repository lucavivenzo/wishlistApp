$(function() {
    $.ajax({
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
                    window.location.replace('event.html?id='+wishlistE.value);
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