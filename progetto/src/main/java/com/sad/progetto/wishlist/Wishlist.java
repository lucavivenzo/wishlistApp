package com.sad.progetto.wishlist;

import com.sad.progetto.event.Event;

import java.util.List;

public class Wishlist {
    private Long id;
    private String name;
    private String description;
    private Integer size;

    private List<Event> events;

    public Wishlist() {
    }

    public Wishlist(Long id, String name, String description, Integer size, List<Event> events) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.size = size;
        this.events = events;
    }

    public Wishlist(String name, String description, Integer size, List<Event> events) {
        this.name = name;
        this.description = description;
        this.size = size;
        this.events = events;
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

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
