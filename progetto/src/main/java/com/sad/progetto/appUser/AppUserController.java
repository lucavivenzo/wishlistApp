package com.sad.progetto.appUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppUserController {

    @Autowired
    AppUserRepository repository;

    @GetMapping(path="temp/usernew")
    public AppUser boh(){
        AppUser utente=new AppUser("Mertens","mertens@napoli", "napolipersempre");
        return repository.save(utente);
    }


}
