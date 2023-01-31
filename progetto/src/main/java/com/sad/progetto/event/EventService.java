package com.sad.progetto.event;

import com.sad.progetto.appUser.AppUser;
import com.sad.progetto.appUser.AppUserRepository;
import com.sad.progetto.wishlist.Wishlist;
import com.sad.progetto.wishlist.WishlistRepository;
import com.sad.progetto.wishlist.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EventService {

    @Autowired
    EventRepository eventRepository;
    @Autowired
    AppUserRepository appUserRepository;
    @Autowired
    WishlistRepository wishlistRepository;


    public Event createEvent(Long wishlistId, String name, String description, String dateString, String eventAddress){
        LocalDate date=LocalDate.parse(dateString);
        AppUser organizer=appUserRepository.findById(1L).get();//CAMBIARE OBV
        Wishlist wishlist;
        if(wishlistId==null){
            wishlist=new Wishlist("Wishlist dell'evento: "+name,"Wishlist autogenerata per l'evento "+name);
            wishlist.setOwner(organizer);
            organizer.addWishlist(wishlist);
        }
        else{
            wishlist=wishlistRepository.findById(wishlistId).get();
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
        if (eventRepository.findById(id).isPresent()){
            Event event=eventRepository.findById(id).get();
            AppUser organizer=event.getOrganizer();
            Wishlist wishlist=event.getWishlist();
            organizer.removeOrganizedEvent(event);
        }
    }
}
