package com.sad.progetto.event;

import java.time.LocalDate;

public class Event {
    private Long id;
    private String name;
    private String description;
    private LocalDate date;
    private String eventAddress;

    public Event(Long id, String name, String description, LocalDate date, String eventAddress) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.eventAddress = eventAddress;
    }

    public Event() {
    }

    public Event(String name, String description, LocalDate date, String eventAddress) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.eventAddress = eventAddress;
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
