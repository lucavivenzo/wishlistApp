package com.sad.progetto.wishlist;

import com.sad.progetto.present.Present;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping(path = "{wishlistId}")
    public Wishlist getWishlist(@PathVariable("wishlistId") Long id) {
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
    public void removePresent(@PathVariable("wishlistId") Long wishlistId, @PathVariable("presentId") Long presentId) {
        wishlistService.removePresent(wishlistId, presentId);
    }

    @GetMapping(path = "{wishlistId}/allpresents")
    public List<Present> getAllPresents(@PathVariable("wishlistId") Long wishlistId) {
        return wishlistService.getAllPresents(wishlistId);
    }


    @GetMapping("/friendswishlist")
    public List<Wishlist> getFriendsWishlist() {
        return wishlistService.getFriendsWishlist();
    }
}
