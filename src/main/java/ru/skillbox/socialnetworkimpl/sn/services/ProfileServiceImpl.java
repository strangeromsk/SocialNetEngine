package ru.skillbox.socialnetworkimpl.sn.services;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetworkimpl.sn.api.requests.PersonEditBody;
import ru.skillbox.socialnetworkimpl.sn.api.requests.PostRequestBody;
import ru.skillbox.socialnetworkimpl.sn.api.responses.PersonResponse;
import ru.skillbox.socialnetworkimpl.sn.api.responses.ResponsePlatformApi;
import ru.skillbox.socialnetworkimpl.sn.domain.Person;
import ru.skillbox.socialnetworkimpl.sn.repositories.PersonRepository;
import ru.skillbox.socialnetworkimpl.sn.services.interfaces.AccountService;
import ru.skillbox.socialnetworkimpl.sn.services.interfaces.ProfileService;
import ru.skillbox.socialnetworkimpl.sn.services.mappers.PersonMapper;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@Slf4j
@Service
public class ProfileServiceImpl implements ProfileService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private AccountServiceImpl accountService;
    @Autowired
    private PersonMapper personMapper;

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(PRIVATE);
        return mapper;
    }

    public PersonResponse mapPerson(Person person){
        return personMapper.toDto(person);
    }

    @Override
    public ResponseEntity<ResponsePlatformApi> getCurrentUser(HttpSession session) {
        Person person = accountService.getCurrentUser("paul@mail.ru");
        PersonResponse personResponse = mapPerson(person);
        return new ResponseEntity<>(ResponsePlatformApi.builder().error("string")
                .timestamp(new Date().getTime()).data(personResponse)
                .build(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponsePlatformApi> editCurrentUser(HttpSession session, PersonEditBody personEditBody) {
        return null;
    }

    @Override
    public ResponseEntity<ResponsePlatformApi> deleteCurrentUser(HttpSession session) {
        return null;
    }

    @Override
    public ResponseEntity<ResponsePlatformApi> getUserById(HttpSession session, long id) {
//        boolean isAuthorized = true;
//        if (!isAuthorized)
//            return accountService.getUserInvalidResponse();
//
//        Person person = personRepository.findPersonById(id);
//        PersonResponse personResponse = mapPerson(person);
//        return new ResponseEntity<>(ResponsePlatformApi.builder().error("string")
//                .timestamp(new Date().getTime()).data(personResponse)
//                .build(), HttpStatus.OK);
        return  null;
    }

    @Override
    public ResponseEntity<ResponsePlatformApi> getPersonsWallPostsByUserId(HttpSession session, long id,
                                                                           int offset, int itemPerPage) {
        return null;
    }

    @Override
    public ResponseEntity<ResponsePlatformApi> addPostToUsersWall(HttpSession session, long id, int publishDate,
                                                                  PostRequestBody postRequestBody) {
        return null;
    }

    @Override
    public ResponseEntity<ResponsePlatformApi> searchPerson(HttpSession session, String firstName,
                                                            String lastName, int ageFrom, int ageTo, int countryId,
                                                            int cityId, int offset, int itemPerPage) {
        // TODO Заглушка на проверку пользователя
        boolean isAuthorized = true;
        if (!isAuthorized)
            return accountService.getUserInvalidResponse();
        //TODO BAD REQUEST
        List<Person> list = personRepository.findPersons(firstName, lastName, ageFrom, ageTo, countryId, cityId, offset, itemPerPage);
        List<PersonResponse> personResponseList = list.stream()
                            .map(this::mapPerson)
                            .collect(Collectors.toCollection(ArrayList::new));
        return new ResponseEntity<>(ResponsePlatformApi.builder().error("string")
                .timestamp(new Date().getTime()).total(personResponseList.size()).offset(offset)
                .perPage(itemPerPage).data(personResponseList)
                .build(), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<ResponsePlatformApi> blockUserById(HttpSession session, int id) {
        personRepository.blockUserById(id);
        //TODO Заглушка на проверку пользователя
        boolean isAuthorized = true;
        if (!isAuthorized)
            return accountService.getUserInvalidResponse();
        return new ResponseEntity<>(accountService.getOkResponse(), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<ResponsePlatformApi> unblockUserById(HttpSession session, int id) {
        personRepository.unBlockUserById(id);
        // Заглушка на проверку пользователя
        boolean isAuthorized = true;
        if (!isAuthorized)
            return accountService.getUserInvalidResponse();
        return new ResponseEntity<>(accountService.getOkResponse(), HttpStatus.OK);
    }
}
