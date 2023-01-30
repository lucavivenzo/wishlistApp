package com.sad.progetto.appUser;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sad.progetto.friendship.Friendship;
import com.sad.progetto.event.Event;
import com.sad.progetto.wishlist.Wishlist;
import jakarta.persistence.*;

import java.util.Set;

@Entity
public class AppUser {
    @Id
    @SequenceGenerator(name="userSequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSequence")
    private Long id;
    private String username;
    private String email;
    private String password;
    @OneToMany
    @JsonIgnore
    private Set<Friendship> friendships;
    @OneToMany
    @JsonIgnore
    private Set<Event> organizedEvents;
    @ManyToMany
    @JsonIgnore
    private Set<Event> events;
    @OneToMany
    @JsonIgnore
    private Set<Wishlist> wishlists;

    public AppUser() {
    }

    public AppUser(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public AppUser(String username, String email, String password, Set<Friendship> friendships, Set<Event> organizedEvents, Set<Event> events) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.friendships = friendships;
        this.organizedEvents = organizedEvents;
        this.events = events;
    }

    public Set<Event> getOrganizedEvents() {
        return organizedEvents;
    }

    public void setOrganizedEvents(Set<Event> organizedEvents) {
        this.organizedEvents = organizedEvents;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    public Set<Friendship> getFriendships() {
        return friendships;
    }

    public void setFriendships(Set<Friendship> friendships) {
        this.friendships = friendships;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public Set<Wishlist> getWishlists() {
        return wishlists;
    }

    public void setWishlists(Set<Wishlist> wishlists) {
        this.wishlists = wishlists;
    }


    public Boolean addOrganizedEvent(Event organizedEvent){
        return organizedEvents.add(organizedEvent);
    }

    public Boolean removeOrganizedEvent(Event organizedEvent){
        return organizedEvents.remove(organizedEvent);
    }

    public Boolean addEvent(Event event){
        return events.add(event);
    }

    public Boolean removeEvent(Event event){
        return events.remove(event);
    }

    public Boolean addWishlist(Wishlist wishlist){
        return wishlists.add(wishlist);
    }

    public Boolean removeWishlist(Wishlist wishlist){
        return wishlists.remove(wishlist);
    }

    public Boolean addFriendship(Friendship friendship){
        return friendships.add(friendship);
    }

    public Boolean removeFriendship(Friendship friendship){
        return friendships.remove(friendship);
    }


}
