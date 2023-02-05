package com.sad.progetto.event;

import com.sad.progetto.appUser.AppUser;
import com.sad.progetto.appUser.AppUserRepository;
import com.sad.progetto.wishlist.Wishlist;
import com.sad.progetto.wishlist.WishlistRepository;
import com.sad.progetto.wishlist.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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


    public Event createEvent(Long wishlistId, String name, String description, String dateString, String eventAddress){
        String email= SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser organizer=appUserRepository.findUserByEmail(email);
        LocalDate date=LocalDate.parse(dateString);
        Wishlist wishlist;
        if(wishlistId==null){
            wishlist=new Wishlist("Wishlist dell'evento: "+name,"Wishlist autogenerata per l'evento "+name);
            wishlist.setOwner(organizer);
            organizer.addWishlist(wishlist);
        }
        else{
            List<Wishlist> ownedWishlists = wishlistRepository.getAllOwnedWishlistsFromEmail(email);
            wishlist = ownedWishlists.stream().filter(wishlist1 -> wishlistId.equals(wishlist1.getId())).findFirst().orElse(null);
            if(wishlist!=null){
                System.out.println("wishlist non tua o non esistente");
                return null;
            }
        }

        Event event=new Event(name,description,date,eventAddress,organizer,wishlist);//questa wishlist che abbiamo passato non ha riferimento all'evento, fa niente? la aggiorno dopo, ma c'è bisogno di riaggiornare anche Event? o non c'è bisogno neanche di aggiornare Wishlist?
        organizer.addOrganizedEvent(event);
        wishlist.setEvent(event);
        eventRepository.save(event);
        appUserRepository.save(organizer);
        wishlistRepository.save(wishlist);
        return event;
    }

    public void deleteEvent(Long id){
        String email=SecurityContextHolder.getContext().getAuthentication().getName();
        List<Long> organizedEvents = eventRepository.findAllOrganizedEventsFromEmail(email);
        if (organizedEvents.contains(id)){
            Event event=eventRepository.findById(id).get();
            AppUser organizer=event.getOrganizer();
            Set<AppUser> guests=event.getGuests();
            Wishlist wishlist=event.getWishlist();
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
        }
        else {
            System.out.println("evento non tuo o inesistente");
        }
    }

    public Event getEvent(Long id){//TODO: dovrebbe restituire eventi tuoi o dei tuoi amici
        if(eventRepository.findById(id).isPresent())
            return eventRepository.findById(id).get();
        else return null;
    }

}
