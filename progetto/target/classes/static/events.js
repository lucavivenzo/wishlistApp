$(function() {

    $(".card:first").hide()
    $.ajax({
    url: "event/myinvitations",
    success: function(result) {
      $.each(result, function(index, item) {
        var id = item.id;
        var name = item.name;
        var description = item.description;
        var eventDate = item.date;
        var eventAddress = item.eventAddress;
        var eventOrganizerId = item.organizer.id;
        document.getElementById('nomeEvent').setAttribute("href","friendEvent.html?eventId="+id+"&friendId="+eventOrganizerId);
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
});