let url = new URL(window.location.href);
let pageId = url.searchParams.get("id");

let nomeR = document.getElementsByName('inputNome')
let descrizioneR = document.getElementsByName('inputDescrizione')
let linkR = document.getElementsByName('inputLink')

console.log(nomeR)
console.log(descrizioneR)
console.log(linkR)

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
        $(cards).find(".card-title").html(name);
        $(cards).find(".card-text").html(description);
        $.each(presents, function(index2, item2){
            $(cards).find(".list-group").append("<li class='list-group-item'>"+item2.name+" "+item2.description+" "+item2.link+"</li>");
        })
        $(cards).show() //show cards
        $(cards).appendTo($("#listaWishlists")) //append to container
      }
    });
  });

  function addPresent(){
    if(!nomeR.value) {window.alert('Inserire il nome del regalo'); return}
    if(!descrizioneR.value) {window.alert('Inserire una descrizione del regalo'); return}
    if(!linkR.value) {window.alert('Inserire un link di acquisto'); return}

    $.ajax({
        url: 'wishlist/'+pageId,
        type: 'GET',
        data: {name:nomeR.value, description:descrizioneR.value, link:linkR.value},
        dataType: 'text',
        async: false,
        success: function(result, textStatus, errorThrown) {//result è il token
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