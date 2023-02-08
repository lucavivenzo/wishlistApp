package com.sad.progetto.event;

import com.sad.progetto.appUser.AppUser;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {

    @Test
    void addGuest() {
        // Un evento senza partecipanti, con name = ... , description = ... , date = ... , eventAddress = ...
        Event testEvent = new Event(new String("Name"), new String("Description"), LocalDate.now(), new String("Address"), null, new HashSet<>(), null );
        // Nuovo partecipante
        AppUser guest = new AppUser();
        testEvent.addGuest(guest);

        Event expectedEvent  = new Event(new String("Name"), new String("Description"), LocalDate.now(), new String("Address"), null, Set.of(guest), null);
        assertEquals(expectedEvent, testEvent);
    }

    @Test
    void removeGuest() {
        // Un evento senza partecipanti, con name = ... , description = ... , date = ... , eventAddress = ...
        Event testEvent = new Event(new String("Name"), new String("Description"), LocalDate.now(), new String("Address"), null, new HashSet<>(), null );
        // Nuovo partecipante
        AppUser guest = new AppUser();
        testEvent.addGuest(guest);

        testEvent.removeGuest(guest);

        Event expectedEvent  = new Event(new String("Name"), new String("Description"), LocalDate.now(), new String("Address"), null, new HashSet<>(), null);
        assertEquals(expectedEvent, testEvent);
    }

}