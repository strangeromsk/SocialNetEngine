package ru.skillbox.socialnetworkimpl.sn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetworkimpl.sn.repositories.PersonRepository;
import ru.skillbox.socialnetworkimpl.sn.security.UserDetailsServiceImpl;
import ru.skillbox.socialnetworkimpl.sn.security.jwt.JwtProvider;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @PostMapping("/token")
    public UserDetails signin(@RequestParam("username") final String username) {
        UserDetails token = userDetailsService.loadUserByUsername(username);
        return token;
    }
}
