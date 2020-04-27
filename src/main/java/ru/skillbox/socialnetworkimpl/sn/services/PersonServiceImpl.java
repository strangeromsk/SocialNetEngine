package ru.skillbox.socialnetworkimpl.sn.services;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.skillbox.socialnetworkimpl.sn.domain.Person;
import ru.skillbox.socialnetworkimpl.sn.services.interfaces.PersonService;
import javax.sql.DataSource;


@Component
public class PersonServiceImpl implements PersonService {

    @Autowired
    private DataSource dataSource;

    public Person findByEmail(String email) {
        return new JdbcTemplate(dataSource).queryForObject("select * from person where e_mail = ?", new Object[]{email}, Person.class);
    }
}
