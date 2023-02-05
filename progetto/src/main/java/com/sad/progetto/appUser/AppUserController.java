package com.sad.progetto.appUser;

import com.sad.progetto.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AppUserController {

    @Autowired
    AppUserRepository repository;
    @Autowired
    AppUserService appUserService;

    @GetMapping("/register")
    public ResponseEntity<String> register(@RequestParam (name = "username") String username,
                            @RequestParam(name = "email") String email,
                            @RequestParam(name = "password") String password) {
        Boolean status = appUserService.register(username, email,password);

        if (status) {
            return ResponseEntity.ok("Registered");
        }
        else {
            return ResponseEntity.status(400).body("Error!");
        }
    }

    //TODO: NON RESTITUIRE IL NOME DELL'UTENTE CHE EFFETTUA LA RICERCA E GESTIRE EVENTUALI ERRORI NELLA RESPONSE ENTITY
    @GetMapping("/search")
    public ResponseEntity<List<AppUser>> searchAppUser(@RequestParam(name="pattern") String pattern){
        return ResponseEntity.ok(appUserService.searchAppUser(pattern));
    }

    //  TODO: REGISTER DEFINITIVA CON LA POST, CANCELLARE L'ALTRA
    /*@PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        Boolean status = appUserService.register(registerRequest.getUsername(), registerRequest.getEmail(), registerRequest.getPassword());

        if (status) {
            return ResponseEntity.ok("Registered");
        }
        else {
            return ResponseEntity.status(400).body("Error!");
        }*/


}
