package com.sad.progetto.friendship;

import com.sad.progetto.appUser.AppUser;
import com.sad.progetto.appUser.AppUserRepository;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FriendshipService {

    @Autowired
    FriendshipRepository friendshipRepository;

    @Autowired
    AppUserRepository appUserRepository;

    //NB: quelle per cui bisogna ritornare/settare solo uno dei due AppUser non conviene modificarle con la query,
    //perchè poi non si può risalire a chi è l'user da settare

    public Boolean addFriend(Long friendId) {

        try {
            AppUser newFriend = appUserRepository.findUserById(friendId); //utente da aggiungere

            String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
            AppUser currentUser = appUserRepository.findUserByEmail(currentUserEmail); //utente che fa la richiesta

            Friendship friendship = new Friendship();

            AppUser firstuser = currentUser;
            AppUser seconduser = newFriend;

            Integer state = 1;

            if (currentUser.getId() > newFriend.getId()) {
                firstuser = newFriend;
                seconduser = currentUser;
                state = 2;
            }

            if ((!(friendshipRepository.existsByAppUser1AndAppUser2(firstuser, seconduser))) && (currentUser.getId() != newFriend.getId())) {
                friendship.setFriendshipDate(LocalDate.now());
                friendship.setAppUser1(firstuser);
                friendship.setAppUser2(seconduser);
                friendship.setFriendshipState(state); //pending state
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

        List<Friendship> friendships = friendshipRepository.findByAppUserAndState(currentUser.getId(), 0);
        List<AppUser> friends = new ArrayList<>();

        for (Friendship friendship : friendships) {
            if (friendship.getAppUser1().getId() != currentUser.getId()) {
                friends.add(friendship.getAppUser1());
            }
            else {
                friends.add(friendship.getAppUser2());
            }
        }

        return friends;

        /*//PER OTTIMIZZARE SI POTREBBE FARE UNA QUERY UNICA, FORSE MEGLIO NON FARLO QUA
        List<Friendship> friendsByFirstUser = friendshipRepository.findByAppUser1(currentUser);
        List<Friendship> friendsBySecondUser = friendshipRepository.findByAppUser2(currentUser);
        //List<AppUser> friends = new ArrayList<>();

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
        return friends;*/
    }

    public List<Friendship> getPendingFriendships() {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser currentUser = appUserRepository.findUserByEmail(currentUserEmail);

        List<Friendship> pendingFriendships = friendshipRepository.findByAppUserAndState(currentUser.getId(), 1);
        pendingFriendships.addAll(friendshipRepository.findByAppUserAndState(currentUser.getId(), 2));

        for (Friendship friendship : pendingFriendships) {
            //se l'utente che vuole vedere le proprie pending è l'appuser1 e se nella friendship la richiesta è stata inviata da appuser1
            if (friendship.getAppUser1().getId()==currentUser.getId() && friendship.getFriendshipState()==1) {
                pendingFriendships.remove(friendship);
                //se l'utente che vuole vedere le proprie pending è l'appuser2 e se nella friendship la richiesta è stata inviata da appuser2
            } else if (friendship.getAppUser2().getId()==currentUser.getId() && friendship.getFriendshipState()==2) {
                pendingFriendships.remove(friendship);
            }
        }

        return pendingFriendships;

        /*List<Friendship> friendsByFirstUser = friendshipRepository.findByAppUser1(currentUser);
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

        return pendingFriendship;*/

    }
    public List<Pair<AppUser,String>> getPendingRequests() {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser currentUser = appUserRepository.findUserByEmail(currentUserEmail);

        List<Friendship> friendships = getPendingFriendships();
        List<AppUser> pendingFriends = new ArrayList<>();
        List<LocalDate> pendingDates = new ArrayList<>();

        List<Pair<AppUser,String>> pendingRequests = new ArrayList<>();

        for (Friendship friendship : friendships) {
            if (friendship.getFriendshipState()==1) {
                pendingRequests.add(Pair.with(friendship.getAppUser1(),friendship.getFriendshipDate().toString()));
            }
            else if (friendship.getFriendshipState()==2) {
                pendingRequests.add(Pair.with(friendship.getAppUser2(),friendship.getFriendshipDate().toString()));
            }
        }

        return pendingRequests;

        /*Pair<AppUser,String>

        for (Friendship friendship : friendships) {
            if (friendship.getAppUser1().getId() != currentUser.getId()) {
                pendingFriends.add(friendship.getAppUser1());
                pendingDates.add(friendship.getFriendshipDate());
            }
            else {
                pendingFriends.add(friendship.getAppUser2());
                pendingDates.add(friendship.getFriendshipDate());
            }
        }

        List<String> pendingRequests = new ArrayList<>();

        for (int i=0; i<pendingFriends.size(); i++) {
            pendingRequests.add(pendingFriends.get(i).getUsername()+","+pendingDates.get(i));
        }

        return pendingRequests;*/

        /*List<Friendship> friendsByFirstUser = friendshipRepository.findByAppUser1(currentUser);
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

        return pendingRequests;*/
    }

    public Boolean acceptFriendshipRequest(Long friendId) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser currentUser = appUserRepository.findUserByEmail(currentUserEmail);

        List<Friendship> pendingFriendships = getPendingFriendships();

        for (Friendship friendship : pendingFriendships) {
            if (friendship.getAppUser1().getId()==friendId || friendship.getAppUser2().getId()==friendId) {
                friendship.setFriendshipState(0);
                friendship.setFriendshipDate(LocalDate.now());
                friendshipRepository.save(friendship);
                return true;
            }
        }
        return false;

//        Friendship friendship = friendshipRepository.findByAppUser1AndAppUser2AndState(friendId, currentUser.getId(),0);
//
//        //TODO: CHI INVIA LA RICHIESTA SE LA PUO AUTOACCETTARE. SERVIREBBERO RICHIESTE DA DESTRA E SINISTRA
//        if ((friendship!=null) && (currentUser.getId() != friendId)) {
//            friendship.setFriendshipState(1);
//            friendship.setFriendshipDate(LocalDate.now());
//
//            //TODO: CAPIRE SE SI DEVE FARE O E' SUPERFLUO
//            /*
//            currentUser.addFriendship(friendship);
//            friendship.getAppUser2().addFriendship(friendship);
//
//            appUserRepository.save(currentUser);
//            appUserRepository.save(friendship.getAppUser2());
//             */
//            friendshipRepository.save(friendship);
//            return true;
//        }
//        else {
//            return false;
//        }

        /*List<Friendship> pendingFriendships = getPendingFriendships();
        //OTTIMIZZARE CON UNA QUERY AD HOC
        for (Friendship friendship : pendingFriendships) {
            //caso in cui chi accetta la richiesta è AppUser 1 e chi l'ha fatta è AppUser2
            if ((friendship.getAppUser1().getId() == currentUser.getId()) && (friendship.getAppUser2().getId() == friendId)) {
                friendship.setFriendshipState(1);
                friendship.setFriendshipDate(LocalDate.now());
                //TODO: CAPIRE SE SI DEVE FARE O E' SUPERFLUO
                *//*
                currentUser.addFriendship(friendship);
                friendship.getAppUser2().addFriendship(friendship);

                appUserRepository.save(currentUser);
                appUserRepository.save(friendship.getAppUser2());
                *//*

                friendshipRepository.save(friendship);
                return true;
            }
            //caso in cui chi accetta la richiesta è AppUser2 e chi l'ha fatta è AppUser1
            else if ((friendship.getAppUser1().getId() == friendId) && (friendship.getAppUser2().getId() == currentUser.getId())) {
                friendship.setFriendshipState(1);
                friendship.setFriendshipDate(LocalDate.now());
                friendshipRepository.save(friendship);
                return true;
            }*/
    }

    public Boolean declineFriendshipRequest(Long friendId) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser currentUser = appUserRepository.findUserByEmail(currentUserEmail);

        List<Friendship> pendingFriendships = getPendingFriendships();

        for (Friendship friendship : pendingFriendships) {
            if (friendship.getAppUser1().getId()==friendId || friendship.getAppUser2().getId()==friendId) {

            AppUser appUser2 = friendship.getAppUser2();
            AppUser appUser1 = friendship.getAppUser1();

            appUser1.getFriendships().remove(friendship);
            appUser2.getFriendships().remove(friendship);

            appUserRepository.save(appUser1);
            appUserRepository.save(appUser2);
            friendshipRepository.delete(friendship);

            return true;
            }
        }
        return false;

//        Friendship friendship = friendshipRepository.findByAppUser1AndAppUser2AndState(friendId, currentUser.getId(),0);
//
//        //TODO: CHI INVIA LA RICHIESTA LA PUO RIFIUTARE. SERVIREBBERO RICHIESTE DA DESTRA E SINISTRA
//        if ((friendship!=null) && (currentUser.getId() != friendId)) {
//
//            AppUser appUser1 = friendship.getAppUser1();
//            AppUser appUser2 = friendship.getAppUser2();
//
//            appUser1.getFriendships().remove(friendship);
//            appUser2.getFriendships().remove(friendship);
//
//            appUserRepository.save(appUser1);
//            appUserRepository.save(appUser2);
//            friendshipRepository.delete(friendship);
//
//            return true;
//        }
//        else {
//            return false;
//        }

        /*
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
        */
    }

    public Boolean deleteFriend(Long friendId) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser currentUser = appUserRepository.findUserByEmail(currentUserEmail);

        Friendship friendshipToDelete = friendshipRepository.findByAppUser1AndAppUser2AndState(friendId, currentUser.getId(),0);

        if (friendshipToDelete!=null) {
            AppUser friendToDelete = appUserRepository.findUserById(friendId);

            friendToDelete.getFriendships().remove(friendshipToDelete);
            currentUser.getFriendships().remove(friendshipToDelete);

            appUserRepository.save(friendToDelete);
            appUserRepository.save(currentUser);
            friendshipRepository.delete(friendshipToDelete);

            return true;
        }
        else {
            return false;
        }
    }

}
