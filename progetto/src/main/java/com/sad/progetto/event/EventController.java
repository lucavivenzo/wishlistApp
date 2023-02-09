package com.sad.progetto.event;

import com.sad.progetto.dto.InviteRequest;
import com.sad.progetto.dto.RegisterRequest;
import com.sad.progetto.wishlist.Wishlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="event")
public class EventController {

    @Autowired
    EventService eventService;
    @Autowired
    private EventRepository eventRepository;

    @GetMapping(path="create")
    public Event createEvent(@RequestParam(name="id") Optional<Long> wishlistId, @RequestParam(name="name") String name, @RequestParam(name="description") String description, @RequestParam(name="date") String date, @RequestParam(name="eventAddress") String eventAddress){//date va scritta come "yyyy-MM-dd"
        if(wishlistId.isPresent())
            return eventService.createEvent(wishlistId.get(),name,description,date,eventAddress);
        else
            return eventService.createEvent(null,name,description,date,eventAddress);
    }

    @GetMapping(path="delete/{eventId}")
    public ResponseEntity<String> deleteEvent(@PathVariable("eventId") Long id){
        Boolean deleted = eventService.deleteEvent(id);
        if (deleted) {
            return ResponseEntity.ok("Event deleted.");
        } else {
            return ResponseEntity.status(400).body("Event not deleted");
        }

    }

    @GetMapping(path="edit")
    public ResponseEntity<Event> editEvent(@RequestParam("idEvent") Long idEvent, @RequestParam(name="name") String name, @RequestParam(name="description") String description, @RequestParam(name="date") String date, @RequestParam(name="eventAddress") String eventAddress){
        Event edited = eventService.editEvent(idEvent, name,description, date, eventAddress);
        if (edited!=null) {
            return ResponseEntity.ok(edited);
        } else {
            return ResponseEntity.status(400).body(null);
        }

    }

    @GetMapping(path="addWishlistToEvent")
    public ResponseEntity<Event> addWishlistToEvent(@RequestParam("idWishlist") Long idWishlist, @RequestParam("idEvent") Long idEvent) {
        Event added = eventService.addWishlistToEvent(idWishlist,idEvent);
        if (added!=null) {
            return ResponseEntity.ok(added);
        } else {
            return ResponseEntity.status(400).body(null);
        }
    }

    @GetMapping(path = "{eventId}") //Restituisce il singolo evento se tuo
    public ResponseEntity<Event> getEvent(@PathVariable("eventId") Long id) {
        Event event = eventService.getEvent(id);
        if (event!=null) {
            return ResponseEntity.ok(event);
        } else {
            return ResponseEntity.status(400).body(null);
        }
    }

    @GetMapping(path="myevents") //Restituisce tutti gli eventi che ho organizzato
    public ResponseEntity<List<Event>> listEvents() {
        List<Event> events = eventService.listEvents();
        if (events!=null) {
            return ResponseEntity.ok(events);
        } else {
            return ResponseEntity.status(400).body(null);
        }
    }

    @GetMapping(path="myinvitations") //Restituisce tutti gli eventi a cui sono invitato
    public ResponseEntity<List<Event>> getInvitations() {
        List<Event> events = eventService.getInvitations();
        if (events!=null) {
            return ResponseEntity.ok(events);
        } else {
            return ResponseEntity.status(400).body(null);
        }
    }

    @GetMapping(path="myinvitationsby/{idFriend}")
    public ResponseEntity<List<Event>> getInvitationsByAFriend(@PathVariable("idFriend") Long idFriend) {
        List<Event> events = eventService.getInvitationsByAFriend(idFriend);
        if (events!=null) {
            return ResponseEntity.ok(events);
        } else {
            return ResponseEntity.status(400).body(null);
        }
    }

    @GetMapping(path="invitation") //Restituisce il singolo evento dell'amico se sono invitato
    public ResponseEntity<Event> getInvitation(@RequestParam("idFriend") Long idFriend, @RequestParam("idEvent") Long idEvent) {
        Event event = eventService.getInvitation(idFriend,idEvent);
        if (event!=null) {
            return ResponseEntity.ok(event);
        } else {
            return ResponseEntity.status(400).body(null);
        }
    }

    @PostMapping(path="invite") //Invita un amico all'evento
    public ResponseEntity<String> invite(@RequestBody InviteRequest inviteRequest) {
        Boolean invited = eventService.invites(inviteRequest.getIdFriend(), inviteRequest.getIdEvent());
        if (invited) {
            return ResponseEntity.ok("Friends invited successfully");
        } else {
            return ResponseEntity.status(400).body("Error");
        }
    }

    @GetMapping(path="wishlistfromevent")
    public ResponseEntity<Wishlist> getWishlistFromEvent(@RequestParam(name="idEvent") Long idEvent) {
         return ResponseEntity.ok(eventService.getWishlistFromEvent(idEvent));
    }


}
