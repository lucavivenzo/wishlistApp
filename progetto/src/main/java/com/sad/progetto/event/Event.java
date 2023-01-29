package com.sad.progetto.event;

import com.sad.progetto.user.User;

import java.time.LocalDate;
import java.util.Set;

public class Event {
    private Long id;
    private String name;
    private String description;
    private LocalDate date;
    private String eventAddress;
    private User organizer;
    private Set<User> guests;



    public Event() {
    }

    public Event(String name, String description, LocalDate date, String eventAddress, User organizer, Set<User> guests) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.eventAddress = eventAddress;
        this.organizer = organizer;
        this.guests = guests;
    }

    public User getOrganizer() {
        return organizer;
    }

    public void setOrganizer(User organizer) {
        this.organizer = organizer;
    }

    public Set<User> getGuests() {
        return guests;
    }

    public void setGuests(Set<User> guests) {
        this.guests = guests;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getEventAddress() {
        return eventAddress;
    }

    public void setEventAddress(String eventAddress) {
        this.eventAddress = eventAddress;
    }


}
