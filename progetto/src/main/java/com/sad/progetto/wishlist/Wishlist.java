package com.sad.progetto.wishlist;

import com.sad.progetto.event.Event;
import com.sad.progetto.present.Present;
import com.sad.progetto.user.User;

import java.util.List;
import java.util.Set;

public class Wishlist {
    private Long id;
    private String name;
    private String description;
    private Integer size;

    private Set<Event> events;
    private User owner;
    private Set<Present> presents;

    public Wishlist() {
    }

    public Wishlist(String name, String description, Integer size, Set<Event> events, User owner, Set<Present> presents) {
        this.name = name;
        this.description = description;
        this.size = size;
        this.events = events;
        this.owner = owner;
        this.presents = presents;
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

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Set<Present> getPresents() {
        return presents;
    }

    public void setPresents(Set<Present> presents) {
        this.presents = presents;
    }
}
