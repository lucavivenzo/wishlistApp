package com.sad.progetto.wishlist;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sad.progetto.event.Event;
import com.sad.progetto.present.Present;
import com.sad.progetto.appUser.AppUser;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
public class Wishlist {
    @Id
    @SequenceGenerator(name="wishlistSequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wishlistSequence")
    private Long id;
    private String name;
    private String description;
    private Integer size;
    @OneToOne
    @JoinColumn
    private Event event;
    @ManyToOne
    @JoinColumn
    private AppUser owner;
    @OneToMany(cascade = {CascadeType.ALL})
    private Set<Present> presents;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Wishlist wishlist)) return false;
        return Objects.equals(getId(), wishlist.getId()) && Objects.equals(getName(), wishlist.getName()) && Objects.equals(getDescription(), wishlist.getDescription()) && Objects.equals(getSize(), wishlist.getSize()) && Objects.equals(getEvent(), wishlist.getEvent()) && Objects.equals(getOwner(), wishlist.getOwner()) && Objects.equals(getPresents(), wishlist.getPresents());
    }

    public Wishlist() {
    }

    public Wishlist(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Wishlist(String name, String description, Integer size, Event event, AppUser owner, Set<Present> presents) {
        this.name = name;
        this.description = description;
        this.size = size;
        this.event = event;
        this.owner = owner;
        this.presents = presents;
    }

    public  Wishlist (Set<Present> presents) {this.presents = presents;}

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

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public AppUser getOwner() {
        return owner;
    }

    public void setOwner(AppUser owner) {
        this.owner = owner;
    }

    public Set<Present> getPresents() {
        return presents;
    }

    public void setPresents(Set<Present> presents) {
        this.presents = presents;
    }

    public Boolean addPresent(Present present){
        return presents.add(present);
    }

    public Boolean removePresent(Present present){
        return presents.remove(present);
    }

    @Override
    public String toString() {
        return "Wishlist{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", size=" + size +
                ", event=" + event +
                ", owner=" + owner +
                ", presents=" + presents +
                '}';
    }
}
