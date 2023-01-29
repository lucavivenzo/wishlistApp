package com.sad.progetto.friendship;

import com.sad.progetto.user.User;

import java.time.LocalDate;

public class Friendship {
    private Long id;
    private User user1;
    private User user2;
    private LocalDate firendshipDate;

    public Friendship() {
    }

    public Friendship(User user1, User user2, LocalDate firendshipDate) {
        this.user1 = user1;
        this.user2 = user2;
        this.firendshipDate = firendshipDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public LocalDate getFirendshipDate() {
        return firendshipDate;
    }

    public void setFirendshipDate(LocalDate firendshipDate) {
        this.firendshipDate = firendshipDate;
    }
}
