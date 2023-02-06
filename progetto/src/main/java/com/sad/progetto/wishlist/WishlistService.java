package com.sad.progetto.wishlist;

import com.sad.progetto.appUser.AppUser;
import com.sad.progetto.appUser.AppUserRepository;
import com.sad.progetto.friendship.Friendship;
import com.sad.progetto.friendship.FriendshipRepository;
import com.sad.progetto.present.Present;
import com.sad.progetto.present.PresentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Service
public class WishlistService {

    @Autowired
    AppUserRepository appUserRepository;
    @Autowired
    WishlistRepository wishlistRepository;
    @Autowired
    PresentRepository presentRepository;
    @Autowired
    private FriendshipRepository friendshipRepository;

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

    //Restituisce la wishlist specifica di un amico
    public Wishlist getFriendsWishlist(Long idWishlist){
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser currentUser = appUserRepository.findUserByEmail(currentUserEmail);

        Wishlist wishlist = wishlistRepository.findWishlistById(idWishlist);

        if (wishlist != null) {

            Long ownerId = wishlist.getOwner().getId();

            Friendship friendship = friendshipRepository.findByAppUser1AndAppUser2AndState(ownerId, currentUser.getId(), 1);

            if (friendship != null) {
                //posso vedere la wishlist se sono negli invitati all'evento o se la wishlist non è associata a nessun evento
                if ((wishlist.getEvent()!=null && wishlist.getEvent().getGuests().contains(currentUser)) || (wishlist.getEvent()==null)) {
                    return wishlistRepository.findById(idWishlist).get();
                } else {
                    return null;
                }

            } else {
                return null;
            }
        }
        else {
            return null;
        }
    }

    //Restituisce tutte le wishlist di un amico
    public List<Wishlist> getWishlistsOfAFriend(Long friendId) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser currentUser = appUserRepository.findUserByEmail(currentUserEmail);

        Friendship friendship = friendshipRepository.findByAppUser1AndAppUser2AndState(currentUser.getId(), friendId, 1);
        if (friendship != null) {

            AppUser friend = appUserRepository.findUserById(friendId);

            Set<Wishlist> wishlistsSet = friend.getWishlists();
            List<Wishlist> wishs = new ArrayList<>(wishlistsSet);
            List<Wishlist> wishlists = new ArrayList<>();

            for (Wishlist w : wishs) {
                //se c'è un evento associato alla wishlist, controlla che sia stato invitato
                //altrimenti se non c'è evento associato alla wishlist, restituiscila lo stesso
                if ((w.getEvent()!=null && w.getEvent().getGuests().contains(currentUser)) || (w.getEvent()==null)) {
                    wishlists.add(w);
                }
            }

            return wishlists;
        }

        else {
            return null;
        }


    }

    //Restituisce le wishlist di tutti gli amici
    public List<Wishlist> getFriendsWishlists() {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser currentUser = appUserRepository.findUserByEmail(currentUserEmail);

        List<Wishlist> friendsWishlists = new ArrayList<>();

        Set<Friendship> f = currentUser.getFriendships();
        List<Friendship> friendships = new ArrayList<>(f);

        for (Friendship friend : friendships) {
            //prendo l'ID dell'amico
            Long friendId;
            if (currentUser.getId()!=friend.getAppUser1().getId()) {
                friendId=friend.getAppUser1().getId();
            }
            else {
                friendId=friend.getAppUser2().getId();
            }
            //aggiungo tutte le wishlist visualizzabili
            friendsWishlists.addAll(getWishlistsOfAFriend(friendId));
        }

        return friendsWishlists;
    }

    //Restituisce tutte le wishlist di cui l'utente (in sessione) è OWNER
    public List<Wishlist> getAllWishlists(){
        String email=SecurityContextHolder.getContext().getAuthentication().getName();
        return wishlistRepository.getAllOwnedWishlistsFromEmail(email);
    }

    public Wishlist addPresent(Long wishlistId, String name, String description, String link){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        List<Wishlist> ownedWishlists = wishlistRepository.getAllOwnedWishlistsFromEmail(email);

        Wishlist wishlist = ownedWishlists.stream().filter(wishlist1 -> wishlistId.equals(wishlist1.getId())).findFirst().orElse(null);

        if(wishlist!=null){
            Present present = new Present(name,description,link,wishlist);
            wishlist.addPresent(present);
            presentRepository.save(present);
            return wishlistRepository.save(wishlist);
        }
        else {
            return null;
        }

    }

    public Boolean removePresent(Long wishlistId, Long presentId){
        String email=SecurityContextHolder.getContext().getAuthentication().getName();

        List<Wishlist> ownedWishlists = wishlistRepository.getAllOwnedWishlistsFromEmail(email);

        Wishlist wishlist = ownedWishlists.stream().filter(wishlist1 -> wishlistId.equals(wishlist1.getId())).findFirst().orElse(null);

        if(wishlist!=null){ //se wishlist tua ed esiste
            Optional<Present> optionalPresent=presentRepository.findById(presentId);

            if (optionalPresent.isPresent() && optionalPresent.get().getWishlist().getId()==wishlistId) {
                Present present=optionalPresent.get();
                System.out.println(wishlist.toString());
                wishlist.removePresent(present);
                presentRepository.delete(present);
                wishlistRepository.save(wishlist);
                return true;
            }
            else {
                return false;
            }
        } else {
            return false;
        }
    }

    public List<Present> getAllPresents(Long wishlistId){   //devono poterla fare gli amici
        Wishlist wishlist=wishlistRepository.findById(wishlistId).get();
        return presentRepository.findByWishlist(wishlist);
    }

    public Boolean buy (Long idPresent) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser currentUser = appUserRepository.findUserByEmail(currentUserEmail);

//        devo verificare che:
//        - il regalo esista
//        - il regalo faccia effettivamente parte della wishlist;
//        - la wishlist sia di un mio amico (in realtà non basta, devo appartenere all'evento se esiste)
//        - il regalo non deve essere già stato acquistato

        Present present = presentRepository.findPresentById(idPresent);

        //verifica che il regalo esiste
        if (present!=null) {
            Wishlist wishlist = present.getWishlist();
            if (wishlist != null) {
                //verifica se il current user è amico dell'owner della wishlist
                Friendship friendship = friendshipRepository.findByAppUser1AndAppUser2AndState(wishlist.getOwner().getId(), currentUser.getId(), 1);
                if (friendship!=null) {
                    present.setState(true);
                    presentRepository.save(present);
                    return true;
                } else {
                    return false;
                }

            } else {
                return false;
            }

        } else {
            return false;
        }
    }

}
