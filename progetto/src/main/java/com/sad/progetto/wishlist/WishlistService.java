package com.sad.progetto.wishlist;

import com.sad.progetto.appUser.AppUser;
import com.sad.progetto.appUser.AppUserRepository;
import com.sad.progetto.present.Present;
import com.sad.progetto.present.PresentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
        String email=SecurityContextHolder.getContext().getAuthentication().getName();
        //System.out.println(email);
        AppUser appUser=appUserRepository.findUserByEmail(email);
        Wishlist wishlist=new Wishlist(name,description);
        wishlist.setOwner(appUser);
        appUser.addWishlist(wishlist);
        wishlistRepository.save(wishlist);
        appUserRepository.save(appUser);
        return wishlist;
    }

    public void removeWishlist(Long id){
        String email=SecurityContextHolder.getContext().getAuthentication().getName();
        List<Long> ownedWishlists = wishlistRepository.findAllOwnedWishlistsFromEmail(email);
        if (ownedWishlists.contains(id)){
            Wishlist wishlist=wishlistRepository.findById(id).get();
            AppUser user=wishlist.getOwner();
            System.out.println(user.toString());
            user.removeWishlist(wishlist);
            wishlistRepository.delete(wishlist);
            appUserRepository.save(user);
            }
        return;

    }

    public Wishlist getWishlist(Long id){//restituisce wishlist tue o dei tuoi amici
        if(wishlistRepository.findById(id).isPresent())
            return wishlistRepository.findById(id).get();
        else return null;
    }

    public List<Wishlist> getAllWishlists(){
        String email=SecurityContextHolder.getContext().getAuthentication().getName();
        return wishlistRepository.getAllOwnedWishlistsFromEmail(email);
    }

    public Wishlist addPresent(Long wishlistId, String name, String description, String link){
        String email=SecurityContextHolder.getContext().getAuthentication().getName();
        List<Wishlist> ownedWishlists = wishlistRepository.getAllOwnedWishlistsFromEmail(email);
        Wishlist wishlist = ownedWishlists.stream().filter(wishlist1 -> wishlistId.equals(wishlist1.getId())).findFirst().orElse(null);
        if(wishlist!=null){
            Present present = new Present(name, description, link, wishlist);
            wishlist.addPresent(present);
            presentRepository.save(present);
            return wishlistRepository.save(wishlist);
        }
        else {
            return null;
        }

    }

    public void removePresent(Long wishlistId, Long presentId){
        String email=SecurityContextHolder.getContext().getAuthentication().getName();
        List<Wishlist> ownedWishlists = wishlistRepository.getAllOwnedWishlistsFromEmail(email);
        Wishlist wishlist = ownedWishlists.stream().filter(wishlist1 -> wishlistId.equals(wishlist1.getId())).findFirst().orElse(null);
        if(wishlist!=null){//se wishlist tua ed esiste
            Optional<Present> optionalPresent=presentRepository.findById(presentId);
            if (optionalPresent.isPresent() && optionalPresent.get().getWishlist().getId()==wishlistId){
                Present present=optionalPresent.get();
                System.out.println(wishlist.toString());
                wishlist.removePresent(present);
                presentRepository.delete(present);
                wishlistRepository.save(wishlist);
            }
        }
        else{
           //errore
        }

    }

    public List<Present> getAllPresents(Long wishlistId){//devono poterla fare gli amici
        Wishlist wishlist=wishlistRepository.findById(wishlistId).get();
        return presentRepository.findByWishlist(wishlist);
    }


}
