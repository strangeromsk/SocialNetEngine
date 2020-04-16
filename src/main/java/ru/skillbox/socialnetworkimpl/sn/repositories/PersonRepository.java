package ru.skillbox.socialnetworkimpl.sn.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.skillbox.socialnetworkimpl.sn.domain.Person;

public interface PersonRepository extends CrudRepository<Person, Integer> {
}
