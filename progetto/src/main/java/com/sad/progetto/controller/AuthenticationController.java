package com.sad.progetto.controller;

import com.sad.progetto.appUser.AppUser;
import com.sad.progetto.appUser.AppUserRepository;
import com.sad.progetto.config.JwtUtils;
import com.sad.progetto.dto.AuthenticationRequest;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class    AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final AppUserRepository appUserRepository;
    private final JwtUtils jwtUtils;

    @PostMapping
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {

        try {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
        ); }

        catch (Exception e) {
            return ResponseEntity.status(401).body("Login failed");
        }

        final AppUser appUser = appUserRepository.findUserByEmail(authenticationRequest.getEmail());
        if (appUser != null) {
            User user = new User(appUser.getEmail(), appUser.getPassword(), Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
            //return ResponseEntity.ok(jwtUtils.generateToken(user));
            HttpHeaders headers=new HttpHeaders();
            headers.add("Set-Cookie","access_token="+jwtUtils.generateToken(user));
            return new ResponseEntity<String>("",headers, HttpStatus.OK);
        }
        return ResponseEntity.status(400).body("Some error has occurred");
    }
}
