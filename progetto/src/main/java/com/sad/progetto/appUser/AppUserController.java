package com.sad.progetto.appUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppUserController {

    @Autowired
    AppUserRepository repository;
    @Autowired
    AppUserService appUserService;

    @GetMapping(path="temp/usernew")
    public AppUser boh(){
        AppUser utente=new AppUser("Mertens","mertens@napoli", "napolipersempre");
        return repository.save(utente);
    }

    @GetMapping("/register")
    public AppUser register(@RequestParam (name = "username") String username,
                            @RequestParam(name = "email") String email,
                            @RequestParam(name = "password") String password) {
        return appUserService.register(username, email,password);
    }

//    @GetMapping(path = "/login")
//    public String login(@RequestParam(name = "email") String email,
//                        @RequestParam(name = "password") String password) {
//        AppUser appUser = appUserService.login(email, password);
//        if (appUser == null)
//            return null;
//        return //appUser.getSession();
//    }


}
