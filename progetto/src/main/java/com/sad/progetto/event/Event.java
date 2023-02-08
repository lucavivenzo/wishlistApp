package com.sad.progetto.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sad.progetto.appUser.AppUser;
import com.sad.progetto.wishlist.Wishlist;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class Event {
    @Id
    @SequenceGenerator(name="eventSequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "eventSequence")
    private Long id;
    private String name;
    private String description;
    private LocalDate date;
    private String eventAddress;
    @ManyToOne
    @JoinColumn
    private AppUser organizer;
    @ManyToMany
    private Set<AppUser> guests;
    @OneToOne(cascade = {CascadeType.ALL})
    @JsonIgnore
    private Wishlist wishlist;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event event)) return false;
        return Objects.equals(getId(), event.getId()) && Objects.equals(getName(), event.getName()) && Objects.equals(getDescription(), event.getDescription()) && Objects.equals(getDate(), event.getDate()) && Objects.equals(getEventAddress(), event.getEventAddress()) && Objects.equals(getOrganizer(), event.getOrganizer()) && Objects.equals(getGuests(), event.getGuests()) && Objects.equals(getWishlist(), event.getWishlist());
    }

    public Event() {
    }

    public Event(String name, String description, LocalDate date, String eventAddress, AppUser organizer, Set<AppUser> guests, Wishlist wishlist) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.eventAddress = eventAddress;
        this.organizer = organizer;
        this.guests = guests;
        this.wishlist = wishlist;
    }

    public Event(String name, String description, LocalDate date, String eventAddress, AppUser organizer, Wishlist wishlist) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.eventAddress = eventAddress;
        this.organizer = organizer;
        this.wishlist = wishlist;
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

    public AppUser getOrganizer() {
        return organizer;
    }

    public void setOrganizer(AppUser organizer) {
        this.organizer = organizer;
    }

    public Set<AppUser> getGuests() {
        return guests;
    }

    public void setGuests(Set<AppUser> guests) {
        this.guests = guests;
    }

    public Wishlist getWishlist() {
        return wishlist;
    }


    public void setWishlist(Wishlist wishlist) {
        this.wishlist = wishlist;
    }

    public Boolean addGuest(AppUser guest){
        return guests.add(guest);
    }
    public Boolean removeGuest(AppUser guest){
        return guests.remove(guest);
    }

}
