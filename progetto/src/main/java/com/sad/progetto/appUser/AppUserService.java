package com.sad.progetto.appUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserService {
    @Autowired
    AppUserRepository appUserRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public AppUser register(String username, String email, String password) {
        if(!checkIfUserExist(email)) {
            AppUser appUser = new AppUser(username, email, passwordEncoder.encode(password));
            return appUserRepository.save(appUser);
        }
        else {
            return null;
        }
    }

    public boolean checkIfUserExist(String email) {
        return appUserRepository.findUserByEmail(email) != null;
    }

}
