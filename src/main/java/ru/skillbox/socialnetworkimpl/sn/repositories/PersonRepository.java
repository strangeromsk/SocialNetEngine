package ru.skillbox.socialnetworkimpl.sn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skillbox.socialnetworkimpl.sn.domain.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer>
{
    Person findByEmail (String email);
}
