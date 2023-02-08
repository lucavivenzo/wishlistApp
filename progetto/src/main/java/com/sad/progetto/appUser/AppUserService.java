package com.sad.progetto.appUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserService {
    @Autowired
    AppUserRepository appUserRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public Boolean register(String username, String email, String password) {
        if(!checkIfUserExist(email)) {
            if (username!=null && password != null) {
                AppUser appUser = new AppUser(username, email, passwordEncoder.encode(password));
                appUserRepository.save(appUser);
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    public boolean checkIfUserExist(String email) {
        return appUserRepository.findUserByEmail(email) != null;
    }

    public List<AppUser> searchAppUser(String pattern){
        return appUserRepository.searchAppUserByUsername('%'+pattern+'%');
    }

    public AppUser getAppUser(Long id){
        return appUserRepository.findUserById(id);
    }

    public AppUser getCurrentAppUser(){
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return appUserRepository.findUserByEmail(currentUserEmail);
    }

}
