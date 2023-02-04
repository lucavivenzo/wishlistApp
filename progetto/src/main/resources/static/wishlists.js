$(function() {
  //hide first div or remove after append using `$(".card:first").remove()`
  $(".card:first").hide()
  $.ajax({
    url: "wishlist/all",
    success: function(result) {
      $.each(result, function(index, item) {
        var cards = $(".card:first").clone() //clone first divs
        var id = item.id;
        var name = item.name;
        var description = item.description;
        var evento = item.evento;
        var presents = item.presents;
        //add values inside divs
        //$(cards).find(".card-header").html(name);
        $(cards).find(".card-title").html(name);
        $(cards).find(".card-text").html(description);
        $.each(presents, function(index2, item2){
            $(cards).find(".list-group").append("<li class='list-group-item'>"+item2.name+" "+item2.description+" "+item2.link+"</li>");
        })
        $(cards).show() //show cards
        $(cards).appendTo($(".container")) //append to container
      });
    }
  });
});