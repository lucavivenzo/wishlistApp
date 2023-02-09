package com.sad.progetto.event;

import com.sad.progetto.appUser.AppUser;
import com.sad.progetto.appUser.AppUserRepository;
import com.sad.progetto.friendship.Friendship;
import com.sad.progetto.friendship.FriendshipRepository;
import com.sad.progetto.wishlist.Wishlist;
import com.sad.progetto.wishlist.WishlistRepository;
import com.sad.progetto.wishlist.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class EventService {

    @Autowired
    EventRepository eventRepository;
    @Autowired
    AppUserRepository appUserRepository;
    @Autowired
    WishlistRepository wishlistRepository;
    @Autowired
    private FriendshipRepository friendshipRepository;

    public Event createEvent(Long wishlistId, String name, String description, String dateString, String eventAddress) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        AppUser organizer = appUserRepository.findUserByEmail(email);
        LocalDate date = LocalDate.parse(dateString);

        Wishlist wishlist;

        //caso in cui la wishlist non esiste -> viene creata di default in loco
        if (wishlistId == null) {
            wishlist = new Wishlist("Wishlist dell'evento: " + name, "Wishlist autogenerata per l'evento " + name);
            wishlist.setOwner(organizer);
            organizer.addWishlist(wishlist);

            Event event = new Event(name, description, date, eventAddress, organizer, wishlist);//questa wishlist che abbiamo passato non ha riferimento all'evento, fa niente? la aggiorno dopo, ma c'è bisogno di riaggiornare anche Event? o non c'è bisogno neanche di aggiornare Wishlist?
            organizer.addOrganizedEvent(event);
            wishlist.setEvent(event);

            eventRepository.save(event);
            appUserRepository.save(organizer);
            wishlistRepository.save(wishlist);
            return event;
        } else {
            //se invece viene fornita, allora prende la wishlist dal db, controlla che il proprietario sia lo stesso
            //del current user, ovvero di chi sta creando l'evento

            wishlist = wishlistRepository.findWishlistById(wishlistId);

            if ((wishlist != null) && (wishlist.getOwner().getId() == organizer.getId())) {
                Event event = new Event(name,description,date,eventAddress,organizer,wishlist);//questa wishlist che abbiamo passato non ha riferimento all'evento, fa niente? la aggiorno dopo, ma c'è bisogno di riaggiornare anche Event? o non c'è bisogno neanche di aggiornare Wishlist?
                organizer.addOrganizedEvent(event);
                wishlist.setEvent(event);
                eventRepository.save(event);
                appUserRepository.save(organizer);
                wishlistRepository.save(wishlist);
                return event;
            } else {
                return null;
        }
    }
    }

    public Boolean deleteEvent(Long id){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        List<Long> organizedEvents = eventRepository.findAllOrganizedEventsFromEmail(email);

        if (organizedEvents.contains(id)){

            Event event = eventRepository.findById(id).get();
            AppUser organizer = event.getOrganizer();
            Set<AppUser> guests = event.getGuests();
            Wishlist wishlist = event.getWishlist();

            organizer.removeOrganizedEvent(event);
            organizer.removeWishlist(wishlist);
            appUserRepository.save(organizer);

            for(AppUser user : guests){
                user.removeEvent(event);
                appUserRepository.save(user);
            }

            wishlist.setEvent(null);
            wishlistRepository.delete(wishlist);
            eventRepository.delete(event);

            return true;
        }
        else {
            return false;
        }
    }

    public Event editEvent(Long idEvent, String name, String description, String dateString, String eventAddress) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser currentUser = appUserRepository.findUserByEmail(currentUserEmail);

        Event event = getEvent(idEvent);

        if (event==null) {
            return null;
        }
        else {
            if (name!="")
                event.setName(name);
            if (description!="")
                event.setDescription(description);
            if (dateString!="")
                event.setDate(LocalDate.parse(dateString));
            if (eventAddress!="")
                event.setEventAddress(eventAddress);

            eventRepository.save(event);
            return event;
        }
    }

    public Event addWishlistToEvent (Long idWishlist, Long idEvent) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser currentUser = appUserRepository.findUserByEmail(currentUserEmail);

        Wishlist wishlist = wishlistRepository.findWishlistById(idWishlist);
        Event event = eventRepository.findEventById(idEvent);

        if ((wishlist.getOwner().getId()== currentUser.getId()) && (event.getOrganizer().getId()==currentUser.getId())) {
            event.setWishlist(wishlist);
            wishlist.setEvent(event);
            eventRepository.save(event);
            wishlistRepository.save(wishlist);
            return event;
        }
        return null;
    } //TODO: PROBABILMENTE NON SERVE

    //Restituisce il singolo evento se tuo
    public Event getEvent(Long id){
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser currentUser = appUserRepository.findUserByEmail(currentUserEmail);

        if((eventRepository.findById(id).isPresent()) && (currentUser.getId()==eventRepository.findById(id).get().getOrganizer().getId())) {
            return eventRepository.findById(id).get();
        }
        else return null;
    }

    //Restituisce i propri eventi (di cui si è owner)
    public List<Event> listEvents() {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser currentUser = appUserRepository.findUserByEmail(currentUserEmail);

        Set<Event> temp = currentUser.getOrganizedEvents();
        List<Event> events = new ArrayList<>(temp);

        return events;
    }

    //Restituisce gli eventi a cui sono invitato
    public List<Event> getInvitations () {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser currentUser = appUserRepository.findUserByEmail(currentUserEmail);

        Set<Event> temp = currentUser.getEvents();
        List<Event> events = new ArrayList<>(temp);

        return events;
    }

    //Restituisce tutti gli eventi di un amico se invitato
    public List<Event> getInvitationsByAFriend(Long idFriend) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser currentUser = appUserRepository.findUserByEmail(currentUserEmail);

        Set<Event> temp = currentUser.getEvents();
        List<Event> events = new ArrayList<>(temp);

        List<Event> invitations = new ArrayList<>();
        for (Event event : events) {
            if (event.getOrganizer().getId()==idFriend) {
                invitations.add(event);
            }
        }
        return invitations;
    }

    //Restituisce il singolo evento dell'amico se sono invitato
    public Event getInvitation(Long idFriend, Long idEvent) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser currentUser = appUserRepository.findUserByEmail(currentUserEmail);

        Event invitation;

        List<Event> invitations = getInvitationsByAFriend(idFriend);
        for (Event event : invitations) {
            if (event.getId()==idEvent) {
                invitation = event;
                return event;
            }
        }
        return null;
    }

    public Boolean invite (Long idFriend, Long idEvent) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser currentUser = appUserRepository.findUserByEmail(currentUserEmail);

        //controllare che chi sto invitando è amico e l'evento sia mio amico

        Event event = eventRepository.findEventById(idEvent);
        Friendship friendship = friendshipRepository.findByAppUser1AndAppUser2AndState(currentUser.getId(), idFriend,0);

        if ( (event!=null) && (event.getOrganizer().getId()==currentUser.getId()) && (friendship!=null)) {

            AppUser guest = appUserRepository.findUserById(idFriend);
            guest.addEvent(event);
            appUserRepository.save(guest);
            //TODO: CAPIRE: ma se lo faccio anche qua, non è che lo fa due volte?
            event.addGuest(guest);
            eventRepository.save(event);
            return true;
        }
        else {
            return false;
        }
    }

    public Boolean invites (List<Long> idUsers, Long idEvent) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser currentUser = appUserRepository.findUserByEmail(currentUserEmail);

        Event event = eventRepository.findEventById(idEvent);

        if (event != null) {
            for (Long id : idUsers) {
                invite(id, idEvent);
            }
            return true;
        }
        return false;
    }

    public Wishlist getWishlistFromEvent(Long idEvent) {
        Event event = eventRepository.findEventById(idEvent);
        return event.getWishlist();
    }

}
