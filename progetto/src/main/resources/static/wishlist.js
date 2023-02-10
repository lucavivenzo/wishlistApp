let url = new URL(window.location.href);
let pageId = url.searchParams.get("id");


$(function() {
    //hide first div or remove after append using `$(".card:first").remove()`
    $(".card:first").hide()
    $.ajax({
      url: "wishlist/"+pageId,
      success: function(item) {
        var id = item.id;
        var name = item.name;
        var description = item.description;
        var evento = item.evento;
        var presents = item.presents;
        document.getElementById('nomeWishlist').setAttribute("href","wishlist.html?id="+id);
        var cards = $(".card:first").clone() //clone first divs
        //add values inside divs
        //$(cards).find(".card-header").html(name);
        $(cards).find(".card-header").html("<p class='p-0 m-0 flex-grow-1'>"+name+"</p><button class='btn btn-danger' type='button' onclick='removeWishlist()'>Elimina wishlist</button></div>");
        $(cards).find(".card-text").html(description);
        $.each(presents, function(index2, item2){
          if(item2.state){//se è stato acquistato
            $(cards).find(".list-group").append("<li class='list-group-item d-flex'><p class='p-0 m-0 flex-grow-1'>"+"Regalo "+(index2+1)+": "+item2.name+" <br> Descrizione: "+item2.description+" <br> Link: "+"<a href='"+item2.link+"'>"+item2.link+"</a></p><button type='button' class=' btn btn-success' disabled>Regalo acquistato</button></li>");
          }
          else{
            $(cards).find(".list-group").append("<li class='list-group-item d-flex'><p class='p-0 m-0 flex-grow-1'>"+"Regalo "+(index2+1)+": "+item2.name+" <br> Descrizione: "+item2.description+" <br> Link:  "+"<a href='"+item2.link+"'>"+item2.link+"</a></p><button type='button' class=' btn btn-danger' onclick='removePresent("+item2.id+")'>Elimina regalo</button></li>");
          }
          
          //$(cards).find(".list-group").append("<li class='list-group-item'>"+item2.name+" "+item2.description+" "+item2.link+"</li>");
        })
        $(cards).show() //show cards
        $(cards).appendTo($("#listaWishlists")) //append to container
      }
    });
  });


  function addPresent(){
    let nomeR = document.getElementsByName('inputNome').item(1)
    let descrizioneR = document.getElementsByName('inputDescrizione').item(1)
    let linkR = document.getElementsByName('inputLink').item(1)
    if(!nomeR.value) {window.alert('Inserire il nome del regalo'); return}
    if(!descrizioneR.value) {window.alert('Inserire una descrizione del regalo'); return}
    if(!linkR.value) {window.alert('Inserire un link di acquisto'); return}

    $.ajax({
        url: 'wishlist/'+pageId+"/add",
        type: 'GET',
        data: {name:nomeR.value, description:descrizioneR.value, link:linkR.value},
        success: function(result, textStatus, errorThrown) {
            if(textStatus=='success'){
                window.location.reload();
            }
            else {
                alert("Inserimento fallito. Riprovare.")
            }
        },
        error: function(){alert("Inserimento fallito. Riprovare.")}
    });
}

function removeWishlist(){
  $.ajax({
    url: 'wishlist/delete',
    type: 'GET',
    data: {id:pageId},
    success: function(result, textStatus, errorThrown) {
        if(textStatus=='success'){
          window.location.replace('myWishlists.html');
        }
        else {
            alert("Eliminazione fallita. Riprovare.")
        }
    },
    error: function(){alert("Eliminazione fallita. Riprovare.")}
});
}

function removePresent(idRegalo){
  console.log(idRegalo);
  $.ajax({
    url: 'wishlist/'+pageId+"/delete/"+idRegalo,
    type: 'GET',
    success: function(result, textStatus, errorThrown) {
        if(textStatus=='success'){
            window.location.reload();
        }
        else {
            alert("Eliminazione fallita. Riprovare.")
        }
    },
    error: function(){alert("Eliminazione fallita. Riprovare.")}
});
}