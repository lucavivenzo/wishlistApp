package com.sad.progetto.wishlist;

import com.sad.progetto.present.Present;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="wishlist")
public class WishlistController {

    @Autowired
    WishlistService wishlistService;

    @GetMapping(path = "create")
    public Wishlist createWishlist(@RequestParam(name = "name") String name, @RequestParam(name = "description") String description) {
        return wishlistService.createWishlist(name, description);
    }

    @GetMapping(path = "delete")//DA CAMBIARE IN DELETE
    public void removeWishlist(@RequestParam(name = "id") Long id) {
        wishlistService.removeWishlist(id);
    }

    @GetMapping(path = "edit")
    public ResponseEntity<String> editWishlist(@RequestParam(name = "idWishlist") Long idWishlist, @RequestParam(name = "name") String name, @RequestParam(name = "description") String description) {
        Boolean edited = wishlistService.editWishlist(idWishlist, name, description);
        if(edited) {
            return ResponseEntity.ok("Wishlist edited");
        } else {
            return ResponseEntity.status(400).body("Error");
        }
    }

    @GetMapping(path = "{wishlistId}/friend")
    public ResponseEntity<Wishlist> getFriendsWishlist(@PathVariable("wishlistId") Long wishlistId) {
        Wishlist wishlist = wishlistService.getFriendsWishlist(wishlistId);
        if (wishlist!=null) {
            return new ResponseEntity<Wishlist>(wishlist, HttpStatus.OK);
        } else {
            return new ResponseEntity<Wishlist>((Wishlist) null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path="{wishlistId}")
    public Wishlist getWishlist(@PathVariable("wishlistId") Long id){
        return wishlistService.getWishlist(id);
    }

    @GetMapping(path = "all")
    public List<Wishlist> getAllWishlists() {
        return wishlistService.getAllWishlists();
    }

    @GetMapping(path = "{wishlistId}/add")
    public Wishlist addPresent(@PathVariable("wishlistId") Long id, @RequestParam(name = "name") String name, @RequestParam(name = "description") String description, @RequestParam(name = "link") String link) {
        return wishlistService.addPresent(id, name, description, link);
    }

    @GetMapping(path = "{wishlistId}/delete/{presentId}")//DA CAMBIARE IN DELETE
    public ResponseEntity<String> removePresent(@PathVariable("wishlistId") Long wishlistId, @PathVariable("presentId") Long presentId) {
        Boolean removed = wishlistService.removePresent(wishlistId, presentId);

        if (removed) {
            return ResponseEntity.ok("Present removed");
        }
        else {
            return ResponseEntity.status(400).body("Present not removed");
        }
    }

    @GetMapping(path = "{wishlistId}/allpresents")
    public List<Present> getAllPresents(@PathVariable("wishlistId") Long wishlistId) {
        return wishlistService.getAllPresents(wishlistId);
    }


    @GetMapping("/friendswishlist")
    public List<Wishlist> getFriendsWishlists() {
        return wishlistService.getFriendsWishlists();
    }

    @GetMapping("/wishlistsofafriend")
    public ResponseEntity<List<Wishlist>> getWishlistsOfAFriend(@RequestParam("friendId")Long friendId) {
        List<Wishlist> wishlists = wishlistService.getWishlistsOfAFriend(friendId);

        if (wishlists != null) {
            return new ResponseEntity<List<Wishlist>>(wishlists, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<List<Wishlist>>((List<Wishlist>) null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/buy")
    public ResponseEntity<String> buy(@RequestParam("idPresent")Long idPresent) {
        Boolean purchased = wishlistService.buy(idPresent);
        if (purchased) {
            return ResponseEntity.ok("Purchased");
        } else {
            return ResponseEntity.status(400).body("Not purchased");
        }
    }



}
