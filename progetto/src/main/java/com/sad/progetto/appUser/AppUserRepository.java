package com.sad.progetto.appUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
     AppUser findUserByEmail(String userEmail);
     AppUser findUserById(Long id);

     @Query(value="SELECT * FROM app_user where LOWER(username) LIKE LOWER(?1)", nativeQuery = true)
     List<AppUser> searchAppUserByUsername(String pattern);

//    private final static List<UserDetails> APPLICATION_USERS = Arrays.asList(
//           new AppUser(
//                    "simone@email",
//                    "password",
//                    Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
//            )
//    );

//    public default UserDetails findUserByEmail(String email) {
//        return APPLICATION_USERS
//                .stream()
//                .filter(u -> u.getUsername().equals(email))
//                .findFirst()
//                .orElseThrow(() -> new UsernameNotFoundException("No user was found"))
//                ;
//    }
}
