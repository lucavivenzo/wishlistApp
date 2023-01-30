package com.sad.progetto.friendship;

import com.sad.progetto.user.User;

import java.time.LocalDate;

public class Friendship {
    private Long id;
    private User user1;
    private User user2;
    private LocalDate friendshipDate;

    public Friendship() {
    }

    public Friendship(User user1, User user2, LocalDate friendshipDate) {
        this.user1 = user1;
        this.user2 = user2;
        this.friendshipDate = friendshipDate;
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

    public LocalDate getFriendshipDate() {
        return friendshipDate;
    }

    public void setFriendshipDate(LocalDate friendshipDate) {
        this.friendshipDate = friendshipDate;
    }
}
