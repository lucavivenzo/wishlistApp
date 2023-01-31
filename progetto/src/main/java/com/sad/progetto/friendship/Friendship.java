package com.sad.progetto.friendship;

import com.sad.progetto.appUser.AppUser;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Friendship {
    @Id
    @SequenceGenerator(name="friendshipSequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "friendshipSequence")
    private Long id;
    @ManyToOne
    @JoinColumn
    private AppUser appUser1;
    @ManyToOne
    @JoinColumn
    private AppUser appUser2;
    private LocalDate friendshipDate;

    public Friendship() {
    }

    public Friendship(AppUser appUser1, AppUser appUser2, LocalDate friendshipDate) {
        this.appUser1 = appUser1;
        this.appUser2 = appUser2;
        this.friendshipDate = friendshipDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppUser getUser1() {
        return appUser1;
    }

    public void setUser1(AppUser appUser1) {
        this.appUser1 = appUser1;
    }

    public AppUser getUser2() {
        return appUser2;
    }

    public void setUser2(AppUser appUser2) {
        this.appUser2 = appUser2;
    }

    public LocalDate getFriendshipDate() {
        return friendshipDate;
    }

    public void setFriendshipDate(LocalDate friendshipDate) {
        this.friendshipDate = friendshipDate;
    }
}
