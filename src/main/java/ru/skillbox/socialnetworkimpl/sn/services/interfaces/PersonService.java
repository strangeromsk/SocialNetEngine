package ru.skillbox.socialnetworkimpl.sn.services.interfaces;

import ru.skillbox.socialnetworkimpl.sn.domain.Person;

public interface PersonService {
    Person findByEmail(String email);
}
