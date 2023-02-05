package com.sad.progetto.event;

import com.sad.progetto.wishlist.Wishlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path="event")
public class EventController {

    @Autowired
    EventService eventService;

    @GetMapping(path="create")//se fornisce id wishlist allora collega a quella, altrimenti ne crea una nuova
    public Event createEvent(@RequestParam(name="id") Optional<Long> wishlistId, @RequestParam(name="name") String name, @RequestParam(name="description") String description, @RequestParam(name="date") String date, @RequestParam(name="eventAddress") String eventAddress){//date va scritta come "yyyy-MM-dd"
        if(wishlistId.isPresent())
            return eventService.createEvent(wishlistId.get(),name,description,date,eventAddress);
        else
            return eventService.createEvent(null,name,description,date,eventAddress);
    }

    @GetMapping(path="delete/{eventId}")
    public void deleteEvent(@PathVariable("eventId") Long id){
        eventService.deleteEvent(id);
        return;
    }

    @GetMapping(path = "{eventId}")
    public Event getEvent(@PathVariable("eventId") Long id) {
        return eventService.getEvent(id);
    }

}
