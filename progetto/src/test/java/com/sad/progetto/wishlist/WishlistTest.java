package com.sad.progetto.wishlist;

import com.sad.progetto.appUser.AppUser;
import com.sad.progetto.event.Event;
import com.sad.progetto.present.Present;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class WishlistTest {

    @Test
    void addPresent() {
        // Una wishlist senza regali, con nome = ... description = ... size = ... appuser = ...
        Wishlist testWishlist = new Wishlist(new String("Wishlist1"), new String("Description"), Integer.valueOf(0), null, null, new HashSet<>());
        // Un regalo con nome = ... description = ... link = ... state = ... wishlist = testWishlist
        Present testPresent = new Present(new String("Present1"), new String("Description"), new String("Link"), Boolean.valueOf(false), testWishlist);
        testWishlist.addPresent(testPresent);

        Wishlist expectedWishlist = new Wishlist(new String("Wishlist1"), new String("Description"), Integer.valueOf(0), null, null, Set.of(testPresent));

        assertEquals(expectedWishlist,testWishlist);
    }

    @Test
    void removePresent() {
        // Una wishlist con nome = ... description = ... size = ... appuser = ...
        Wishlist testWishlist = new Wishlist(new String("Wishlist1"), new String("Description"), Integer.valueOf(0), null, null, new HashSet<>());
        // Un regalo con nome = ... description = ... link = ... state = ... wishlist = testWishlist
        Present present = new Present(new String("Present"), new String("Description"), new String("Link"), Boolean.valueOf(false), testWishlist);
        //Aggiunto regalo alla wishlist
        testWishlist.addPresent(present);

        testWishlist.removePresent(present);
        // Una wishlist senza regali con nome = ... description = ... size = ... appuser = ...
        Wishlist expectedWishlist = new Wishlist(new String("Wishlist1"), new String("Description"), Integer.valueOf(0), null, null, new HashSet<>());

        assertEquals(expectedWishlist,testWishlist);
    }
}