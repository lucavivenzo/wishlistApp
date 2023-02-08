package com.sad.progetto.present;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sad.progetto.wishlist.Wishlist;
import jakarta.persistence.*;

@Entity
public class Present {
    @Id
    @SequenceGenerator(name = "presentSequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "presentSequence")
    private Long id;
    private String name;
    private String description;
    private String link;
    private Boolean state; //NON ACQUISTATO = 0, ACQUISTATO = 1
    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private Wishlist wishlist;

    public Present() {
    }

    public Present(Long id, String name, String description, String link, Boolean state, Wishlist wishlist) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.link = link;
        this.state = state;
        this.wishlist = wishlist;
    }

    public Present(String name, String description, String link, Wishlist wishlist) {
        this.name = name;
        this.description = description;
        this.link = link;
        this.state = false;
        this.wishlist = wishlist;
    }

    public Present(String name, String description, String link, Boolean state, Wishlist wishlist) {
        this.name = name;
        this.description = description;
        this.link = link;
        this.state = state;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public Wishlist getWishlist() {
        return wishlist;
    }

    public void setWishlist(Wishlist wishlist) {
        this.wishlist = wishlist;
    }
}

