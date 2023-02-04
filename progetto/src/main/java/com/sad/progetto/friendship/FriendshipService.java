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

        try {
            AppUser newFriend = appUserRepository.findUserById(friendId); //utente da aggiungere


            String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
            AppUser currentUser = appUserRepository.findUserByEmail(currentUserEmail); //utente che fa la richiesta

            Friendship friendship = new Friendship();

            AppUser firstuser = currentUser;
            AppUser seconduser = newFriend;

            if (currentUser.getId() > newFriend.getId()) {
                firstuser = newFriend;
                seconduser = currentUser;
            }

            if (!(friendshipRepository.existsByAppUser1AndAppUser2(firstuser, seconduser))) {
                friendship.setFriendshipDate(LocalDate.now());
                friendship.setAppUser1(firstuser);
                friendship.setAppUser2(seconduser);
                friendship.setFriendshipState(0); //pending state
                friendshipRepository.save(friendship); //salva la friendship

                currentUser.addFriendship(friendship); //aggiunge la friendship all'utente che invia la richiesta di amicizia
                newFriend.addFriendship(friendship); //aggiunge la friendship all'utente che riceve la richiesta di amicizia
                appUserRepository.save(currentUser);
                appUserRepository.save(newFriend);
                return true;
            }
            else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public List<AppUser> getFriends() {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser currentUser = appUserRepository.findUserByEmail(currentUserEmail);
        //PER OTTIMIZZARE SI POTREBBE FARE UNA QUERY UNICA
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

        for (int i=0; i<pendingFriends.size(); i++) {
            pendingRequests.add(pendingFriends.get(i).getUsername()+","+pendingDates.get(i));
        }

        return pendingRequests;

    }

    public List<Friendship> getPendingFriendships() {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser currentUser = appUserRepository.findUserByEmail(currentUserEmail);

        List<Friendship> friendsByFirstUser = friendshipRepository.findByAppUser1(currentUser);
        List<Friendship> friendsBySecondUser = friendshipRepository.findByAppUser2(currentUser);

        List<Friendship> pendingFriendship = new ArrayList<>();

        for (Friendship friendship : friendsByFirstUser) {
            if (friendship.getFriendshipState()==0) {   //solo se l'amicizia è in pending state
                pendingFriendship.add(friendship);
            }
        }

        for (Friendship friendship : friendsBySecondUser) {
            if (friendship.getFriendshipState()==0) {
                pendingFriendship.add(friendship);
            }
        }

        return pendingFriendship;

    }

    public Boolean acceptFriendshipRequest(Long friendId) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser currentUser = appUserRepository.findUserByEmail(currentUserEmail);

        List<Friendship> pendingFriendships = getPendingFriendships();
        //OTTIMIZZARE CON UNA QUERY AD HOC
        for (Friendship friendship : pendingFriendships) {
            //caso in cui chi accetta la richiesta è AppUser 1 e chi l'ha fatta è AppUser2
            if ((friendship.getAppUser1().getId() == currentUser.getId()) && (friendship.getAppUser2().getId() == friendId)) {
                friendship.setFriendshipState(1);
                friendship.setFriendshipDate(LocalDate.now());
                //TODO: CAPIRE SE SI DEVE FARE O E' SUPERFLUO
                /*
                currentUser.addFriendship(friendship);
                friendship.getAppUser2().addFriendship(friendship);

                appUserRepository.save(currentUser);
                appUserRepository.save(friendship.getAppUser2());
                */

                friendshipRepository.save(friendship);
                return true;
            }
            //caso in cui chi accetta la richiesta è AppUser2 e chi l'ha fatta è AppUser1
            else if ((friendship.getAppUser1().getId() == friendId) && (friendship.getAppUser2().getId() == currentUser.getId())) {
                friendship.setFriendshipState(1);
                friendship.setFriendshipDate(LocalDate.now());
                friendshipRepository.save(friendship);
                return true;
            }
        }
        return false;
    }

    public Boolean declineFriendshipRequest(Long friendId) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser currentUser = appUserRepository.findUserByEmail(currentUserEmail);

        List<Friendship> pendingFriendships = getPendingFriendships();

        for (Friendship friendship : pendingFriendships) {
            //caso in cui chi rifiuta la richiesta è AppUser 1 e chi l'ha fatta è AppUser2
            if ((friendship.getAppUser1().getId() == currentUser.getId()) && (friendship.getAppUser2().getId() == friendId)) {

                AppUser requester = friendship.getAppUser2();

                requester.getFriendships().remove(friendship); //cancella dalla lista di chi ha richiesto l'amicizia la friendship
                currentUser.getFriendships().remove(friendship); //cancella dalla lista del current user la friendship

                appUserRepository.save(requester);
                appUserRepository.save(currentUser);
                friendshipRepository.delete(friendship);
                return true;
            }
            //caso in cui chi rifiuta la richiesta è AppUser2 e chi l'ha fatta è AppUser1
            else if ((friendship.getAppUser1().getId() == friendId) && (friendship.getAppUser2().getId() == currentUser.getId())) {

                AppUser requester = friendship.getAppUser1(); //notare che è cambiato rispetto al caso precedente!

                requester.getFriendships().remove(friendship); //cancella dalla lista di chi ha richiesto l'amicizia la friendship
                currentUser.getFriendships().remove(friendship); //cancella dalla lista del current user la friendship

                appUserRepository.save(requester);
                appUserRepository.save(currentUser);
                friendshipRepository.delete(friendship);
                return true;
            }
        }
        return false;
    }

    public Boolean deleteFriend(Long friendId) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser currentUser = appUserRepository.findUserByEmail(currentUserEmail);

        Friendship friendshipToDelete = friendshipRepository.findByAppUser1AndAppUser2(friendId, currentUser.getId());
        AppUser friendToDelete = appUserRepository.findUserById(friendId);

        friendToDelete.getFriendships().remove(friendshipToDelete);
        currentUser.getFriendships().remove(friendshipToDelete);

        appUserRepository.save(friendToDelete);
        appUserRepository.save(currentUser);
        friendshipRepository.delete(friendshipToDelete);

        return true;
    }

}
