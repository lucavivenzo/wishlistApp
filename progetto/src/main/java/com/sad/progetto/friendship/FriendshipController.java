package com.sad.progetto.friendship;

import com.sad.progetto.appUser.AppUser;
import com.sad.progetto.appUser.AppUserRepository;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class FriendshipController {

    @Autowired
    FriendshipService friendshipService;

    @GetMapping("/addFriend")
    public ResponseEntity<String> addFriend(@RequestParam("friendId")Long friendId) throws NullPointerException {

        Boolean added = friendshipService.addFriend(friendId);

        if (added) {
            return ResponseEntity.ok("Richiesta di amicizia inviata. Attendi una risposta");
        }
        else {
            return ResponseEntity.status(400).body("Error. Bad request!");
        }

    }

    @GetMapping("/deletefriend")
    public ResponseEntity<String> deleteFriend(@RequestParam("friendId")Long friendId) throws NullPointerException {

        Boolean deleted = friendshipService.deleteFriend(friendId);

        if (deleted) {
            return ResponseEntity.ok("Friend deleted successfully");
        }
        else {
            return ResponseEntity.status(400).body("Error. Bad request!");
        }

    }

    @GetMapping("/listFriends")
    public ResponseEntity<List<AppUser>> getFriends() {
        List<AppUser> myFriends = friendshipService.getFriends();
        return new ResponseEntity<List<AppUser>>(myFriends, HttpStatus.OK);
    }

    @GetMapping("/listPendingRequests")
    public ResponseEntity<List<Pair<AppUser,String>>> getPendingRequests() {
        List<Pair<AppUser,String>> pendingRequests = friendshipService.getPendingRequests();
        return new ResponseEntity<List<Pair<AppUser,String>>>(pendingRequests, HttpStatus.OK);
    }

    @GetMapping("/setFriendship")
    public ResponseEntity<String> setFriendship(@RequestParam("accept")Boolean set, @RequestParam("friendId")Long friendId) {

        if(set) {
            if (friendshipService.acceptFriendshipRequest(friendId)) {
                return ResponseEntity.ok("Friend added successfully");
            }
            else {
                return ResponseEntity.status(400).body("No friendship request pending");
            }
        }
        else {
            if (friendshipService.declineFriendshipRequest(friendId)) {
                return ResponseEntity.ok("Friendship request declined");
            }
            else {
                return ResponseEntity.status(400).body("No friendship request pending");
            }
        }

    }

}
