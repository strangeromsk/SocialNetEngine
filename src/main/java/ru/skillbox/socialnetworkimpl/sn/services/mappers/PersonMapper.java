package ru.skillbox.socialnetworkimpl.sn.services.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.skillbox.socialnetworkimpl.sn.api.responses.PersonResponse;
import ru.skillbox.socialnetworkimpl.sn.domain.Person;

import java.util.Objects;

@Component
public class PersonMapper{
    @Autowired
    private ModelMapper modelMapper;

    public PersonResponse toDto(Person person){
        return Objects.isNull(person) ? null : modelMapper.map(person, PersonResponse.class);
    }
}
