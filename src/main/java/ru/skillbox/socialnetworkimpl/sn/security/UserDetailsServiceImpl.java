package ru.skillbox.socialnetworkimpl.sn.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetworkimpl.sn.domain.Person;
import ru.skillbox.socialnetworkimpl.sn.repositories.PersonRepository;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PersonRepository personRepository;

    public UserDetailsServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        Person existPerson = personRepository.findByEmail(email);
        return new User(existPerson.getEmail(), existPerson.getPassword(), new ArrayList<>());
    }
}
