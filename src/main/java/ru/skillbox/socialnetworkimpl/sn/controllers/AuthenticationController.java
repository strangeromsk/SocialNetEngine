package ru.skillbox.socialnetworkimpl.sn.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetworkimpl.sn.domain.Person;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import ru.skillbox.socialnetworkimpl.sn.api.requests.AuthenticationRequest;
import ru.skillbox.socialnetworkimpl.sn.services.PersonDetailsService;
import ru.skillbox.socialnetworkimpl.sn.jwtsecurity.JwtUtil;
import ru.skillbox.socialnetworkimpl.sn.services.interfaces.PersonService;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PersonDetailsService userDetailsService;

    @Autowired
    private PersonService personService;

//    method just for testing
//    @GetMapping("/")
//    public String sayHello() {
//        return "Hello";
//    }

    @PostMapping(value = "/login")
    public Person createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {

        String email = authenticationRequest.getEmail();

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, authenticationRequest.getPassword()));

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(email);

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        Person person = personService.findByEmail(email);

        person.setToken(jwt);

        return person;
    }
}
