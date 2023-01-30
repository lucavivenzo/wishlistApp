package com.sad.progetto.wishlist;

import com.sad.progetto.appUser.AppUser;
import com.sad.progetto.appUser.AppUserRepository;
import com.sad.progetto.present.Present;
import com.sad.progetto.present.PresentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WishlistService {

    @Autowired
    AppUserRepository appUserRepository;
    @Autowired
    WishlistRepository wishlistRepository;
    @Autowired
    PresentRepository presentRepository;

    public Wishlist createWishlist(String name, String description){
        AppUser appUser=appUserRepository.findById(1L).get();//CAMBIARE OBV
        System.out.println(appUser.toString());
        Wishlist wishlist=new Wishlist(name,description);
        wishlist.setOwner(appUser);
        appUser.addWishlist(wishlist);
        wishlistRepository.save(wishlist);
        appUserRepository.save(appUser);
        return wishlist;
    }

    public void removeWishlist(Long id){
        if (wishlistRepository.findById(id).isPresent()){
            Wishlist wishlist=wishlistRepository.findById(id).get();
            AppUser user=wishlist.getOwner();
            System.out.println(user.toString());
            user.removeWishlist(wishlist);
            wishlistRepository.delete(wishlist);
            appUserRepository.save(user);
            }
        return;

    }

    public Wishlist getWishlist(Long id){
        if(wishlistRepository.findById(id).isPresent())
            return wishlistRepository.findById(id).get();
        else return null;
    }

    public List<Wishlist> getAllWishlists(){
        return wishlistRepository.findAll();
    }

    public Wishlist addPresent(Long wishlistId, String name, String description, String link){
        Optional<Wishlist> optionalWishlist=wishlistRepository.findById(wishlistId);
        if(optionalWishlist.isPresent()) {
            Wishlist wishlist = optionalWishlist.get();
            Present present = new Present(name, description, link, wishlist);
            wishlist.addPresent(present);
            presentRepository.save(present);
            return wishlistRepository.save(wishlist);
        }
        else return null;

    }

    public void removePresent(Long wishlistId, Long presentId){
        Optional<Wishlist> optionalWishlist=wishlistRepository.findById(wishlistId);
        Optional<Present> optionalPresent=presentRepository.findById(presentId);
        if (optionalPresent.isPresent() && optionalWishlist.isPresent()){
            Present present=optionalPresent.get();
            Wishlist wishlist=present.getWishlist();//funziona sta cosa? sì, quindi possiamo cancellare anche senza l'id della wishlist teoricamente
            System.out.println(wishlist.toString());
            wishlist.removePresent(present);
            presentRepository.delete(present);
            wishlistRepository.save(wishlist);
        }
        return;
    }

    public List<Present> getAllPresents(Long wishlistId){
        Wishlist wishlist=wishlistRepository.findById(wishlistId).get();
        return presentRepository.findByWishlist(wishlist);
    }


}
