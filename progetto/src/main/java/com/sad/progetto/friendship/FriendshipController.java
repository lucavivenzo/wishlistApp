package com.sad.progetto.friendship;

import com.sad.progetto.appUser.AppUser;
import com.sad.progetto.appUser.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FriendshipController {

    @Autowired
    FriendshipService friendshipService;

    @GetMapping("/addFriend")
    public ResponseEntity<String> addFriend(@RequestParam("friendId")Long friendId) throws NullPointerException {

        Boolean added = friendshipService.addFriend(friendId);

        if (added) {
            return ResponseEntity.ok("Friend added. Waiting for response.");
        }
        else {
            return ResponseEntity.ok("Friend not added");
        }

    }

    @GetMapping("/listFriends")
    public ResponseEntity<List<AppUser>> getFriends() {
        List<AppUser> myFriends = friendshipService.getFriends();
        return new ResponseEntity<List<AppUser>>(myFriends, HttpStatus.OK);
    }

    @GetMapping("/listPendingRequests")
    public ResponseEntity<List<String>> getPendingRequests() {
        List<String> pendingRequests = friendshipService.getPendingRequests();
        return new ResponseEntity<List<String>>(pendingRequests, HttpStatus.OK);
    }

}
