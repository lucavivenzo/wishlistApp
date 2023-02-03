package com.sad.progetto.friendship;

import com.sad.progetto.appUser.AppUser;
import com.sad.progetto.appUser.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class FriendshipService {

    @Autowired
    FriendshipRepository friendshipRepository;

    @Autowired
    AppUserRepository appUserRepository;

    public Boolean addFriend(Long friendId) {

        AppUser newFriend = appUserRepository.findUserById(friendId); //utente da aggiungere

        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser currentUser = appUserRepository.findUserByEmail(currentUserEmail); //utente che fa la richiesta

        Friendship friendship = new Friendship();

        AppUser firstuser = currentUser;
        AppUser seconduser = newFriend;

        if (currentUser.getId()>newFriend.getId()) {
            firstuser = newFriend;
            seconduser = currentUser;
        }

        if(!(friendshipRepository.existsByAppUser1AndAppUser2(firstuser,seconduser))) {
            friendship.setFriendshipDate(LocalDate.now());
            friendship.setAppUser1(firstuser);
            friendship.setAppUser2(seconduser);
            friendship.setFriendshipState(0); //pending state
            friendshipRepository.save(friendship);
            return true;
        }
        else {
            return false;
        }
    }

    public List<AppUser> getFriends() {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser currentUser = appUserRepository.findUserByEmail(currentUserEmail);

        List<Friendship> friendsByFirstUser = friendshipRepository.findByAppUser1(currentUser);
        List<Friendship> friendsBySecondUser = friendshipRepository.findByAppUser2(currentUser);
        List<AppUser> friends = new ArrayList<>();

        for (Friendship friendship : friendsByFirstUser) {
            if (friendship.getFriendshipState()==1) {   //solo se l'amicizia non è in pending state
                friends.add(appUserRepository.findUserById(friendship.getAppUser2().getId()));
            }
        }

        for (Friendship friendship : friendsBySecondUser) {
            if (friendship.getFriendshipState()==1) {
                friends.add(appUserRepository.findUserById(friendship.getAppUser1().getId()));
            }
        }

        return friends;

    }

    public List<String> getPendingRequests() {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser currentUser = appUserRepository.findUserByEmail(currentUserEmail);

        Set<Friendship> test = currentUser.getFriendships();

        List<Friendship> friendsByFirstUser = friendshipRepository.findByAppUser1(currentUser);
        List<Friendship> friendsBySecondUser = friendshipRepository.findByAppUser2(currentUser);

        List<AppUser> pendingFriends = new ArrayList<>();
        List<LocalDate> pendingDates = new ArrayList<>();

        for (Friendship friendship : friendsByFirstUser) {
            if (friendship.getFriendshipState()==0) {   //solo se l'amicizia è in pending state
                pendingFriends.add(appUserRepository.findUserById(friendship.getAppUser2().getId()));
                pendingDates.add(friendship.getFriendshipDate());
            }
        }

        for (Friendship friendship : friendsBySecondUser) {
            if (friendship.getFriendshipState()==0) {
                pendingFriends.add(appUserRepository.findUserById(friendship.getAppUser1().getId()));
                pendingDates.add(friendship.getFriendshipDate());
            }
        }

        List<String> pendingRequests = new ArrayList<>();

        Integer size = pendingFriends.size();

        for (int i=0; i<pendingFriends.size(); i++) {
            pendingRequests.add(pendingFriends.get(i).getUsername()+","+pendingDates.get(i));
        }

        return pendingRequests;

    }

}
