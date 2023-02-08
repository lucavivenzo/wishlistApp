package com.sad.progetto.appUser;

import com.sad.progetto.event.Event;
import com.sad.progetto.friendship.Friendship;
import com.sad.progetto.wishlist.Wishlist;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AppUserTest {

    @Test
    void addEvent() {
        //Un utente con name = ... , email = ... , password = ...
        AppUser testUser = new AppUser(new String("Name"), new String("Email"), new String("Password"), new HashSet<>(), new HashSet<>(), new HashSet<>());
        //Un evento con name = ... , description = ... , date = ... , eventAddress = ... ,
        Event event = new Event(new String("Name"), new String("Description"), LocalDate.now(), new String("Address"), null, new HashSet<>(), null);

        testUser.addEvent(event);

        AppUser expectedUser = new AppUser(new String("Name"), new String("Email"), new String("Password"), new HashSet<>(), new HashSet<>(), Set.of(event));
        assertEquals(expectedUser,testUser);
    }

    @Test
    void removeEvent() {
        //Un evento con name = ... , description = ... , date = ... , eventAddress = ...
        Event event = new Event(new String("Name"), new String("Description"), LocalDate.now(), new String("Address"), null, new HashSet<>(), null);
        //Un utente con name = ... , email = ... , password = ...
        AppUser testUser = new AppUser(new String("Name"), new String("Email"), new String("Password"), new HashSet<>(), new HashSet<>(), new HashSet<>());
        testUser.addEvent(event);

        testUser.removeEvent(event);

        AppUser expectedUser = new AppUser(new String("Name"), new String("Email"), new String("Password"), new HashSet<>(), new HashSet<>(), new HashSet<>());
        assertEquals(expectedUser,testUser);
    }

    @Test
    void addWishlist() {
        //Un utente con name = ... , email = ... , password = ...
        AppUser testUser = new AppUser(new String("Name"), new String("Email"), new String("Password"), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        // Una wishlist con nome = ... description = ... size = ... appuser = ..., size = ...
        Wishlist wishlist = new Wishlist(new String("Wishlist1"), new String("Description"), Integer.valueOf(0), null, null, new HashSet<>());

        testUser.addWishlist(wishlist);

        AppUser expectedUser = new AppUser(new String("Name"), new String("Email"), new String("Password"), new HashSet<>(), new HashSet<>(), new HashSet<>(), Set.of(wishlist));
        assertEquals(expectedUser,testUser);
    }

    @Test
    void removeWishlist() {
        //Un utente con name = ... , email = ... , password = ...
        AppUser testUser = new AppUser(new String("Name"), new String("Email"), new String("Password"), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        // Una wishlist con nome = ... description = ... size = ... appuser = ...
        Wishlist wishlist = new Wishlist(new String("Wishlist1"), new String("Description"), Integer.valueOf(0), null, null, new HashSet<>());
        testUser.addWishlist(wishlist);

        testUser.removeWishlist(wishlist);

        AppUser expectedUser = new AppUser(new String("Name"), new String("Email"), new String("Password"), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        assertEquals(expectedUser,testUser);


    }

    @Test
    void addFriendship() {
        //Un utente con name = ... , email = ... , password = ...
        AppUser testUser = new AppUser(new String("Name"), new String("Email"), new String("Password"), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        // Un'amicizia, con id = ... , appuser1 = ... , appuser2 = ... , friendshipDate = ... , friendshipState = ...
        Friendship friendship = new Friendship(Long.valueOf(0), new AppUser(), new AppUser(), LocalDate.now(), Integer.valueOf(0));

        testUser.addFriendship(friendship);

        AppUser expectedUser = new AppUser(new String("Name"), new String("Email"), new String("Password"), Set.of(friendship), new HashSet<>(), new HashSet<>(), new HashSet<>());
        assertEquals(expectedUser,testUser);
    }

    @Test
    void removeFriendship() {
        //Un utente con name = ... , email = ... , password = ...
        AppUser testUser = new AppUser(new String("Name"), new String("Email"), new String("Password"), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        // Un'amicizia, con id = ... , appuser1 = ... , appuser2 = ... , friendshipDate = ... , friendshipState = ...
        Friendship friendship = new Friendship(Long.valueOf(0), new AppUser(), new AppUser(), LocalDate.now(), Integer.valueOf(0));
        testUser.addFriendship(friendship);

        testUser.removeFriendship(friendship);

        AppUser expectedUser = new AppUser(new String("Name"), new String("Email"), new String("Password"), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        assertEquals(expectedUser,testUser);
    }
}