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
    @OneToOne//(cascade = {CascadeType.ALL})
    @JoinColumn
    private AppUser appUser1;
    @OneToOne//cascade = {CascadeType.ALL})
    @JoinColumn
    private AppUser appUser2;
    private LocalDate friendshipDate;
    private Integer friendshipState;

/*  VALUE FOR FRIENDSHIPSTATE:

    0: friends
    1: pending, appUser1 ha inviato la richiesta
    2: pending, appUser2 ha inviato la richiesta

*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppUser getAppUser1() {
        return appUser1;
    }

    public void setAppUser1(AppUser appUser1) {
        this.appUser1 = appUser1;
    }

    public AppUser getAppUser2() {
        return appUser2;
    }

    public void setAppUser2(AppUser appUser2) {
        this.appUser2 = appUser2;
    }

    public LocalDate getFriendshipDate() {
        return friendshipDate;
    }

    public void setFriendshipDate(LocalDate friendshipDate) {
        this.friendshipDate = friendshipDate;
    }

    public Integer getFriendshipState() {
        return friendshipState;
    }

    public void setFriendshipState(Integer friendshipState) {
        this.friendshipState = friendshipState;
    }

    public Friendship() {
    }

    public Friendship(Long id, AppUser appUser1, AppUser appUser2, LocalDate friendshipDate, Integer friendshipState) {
        this.id = id;
        this.appUser1 = appUser1;
        this.appUser2 = appUser2;
        this.friendshipDate = friendshipDate;
        this.friendshipState = friendshipState;
    }
}

