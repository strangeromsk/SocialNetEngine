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
import ru.skillbox.socialnetworkimpl.sn.api.responses.PostResponse;
import ru.skillbox.socialnetworkimpl.sn.api.responses.ReportApi;
import ru.skillbox.socialnetworkimpl.sn.api.responses.ResponsePlatformApi;
import ru.skillbox.socialnetworkimpl.sn.domain.Person;
import ru.skillbox.socialnetworkimpl.sn.domain.Post;
import ru.skillbox.socialnetworkimpl.sn.repositories.PersonRepository;
import ru.skillbox.socialnetworkimpl.sn.repositories.PostRepository;
import ru.skillbox.socialnetworkimpl.sn.services.interfaces.ProfileService;
import ru.skillbox.socialnetworkimpl.sn.services.mappers.DataMapper;
import ru.skillbox.socialnetworkimpl.sn.services.mappers.PersonMapper;
import ru.skillbox.socialnetworkimpl.sn.services.mappers.PersonsMapper;
import ru.skillbox.socialnetworkimpl.sn.services.mappers.PostMapper;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
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
    private PostRepository postRepository;
    @Autowired
    private AccountServiceImpl accountService;
    @Autowired
    private PersonMapper personMapper;
    @Autowired
    private PersonsMapper personsMapper;
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private DataMapper dataMapper;

    @PersistenceContext
    private EntityManager entityManager;

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

    public PersonResponse mapPerson(Person person) {
        return personMapper.toDto(person);
    }

    @Override
    public ResponseEntity<ResponsePlatformApi> getCurrentUser(HttpSession session) {
        Person person = getCurrentUser();
        PersonResponse personResponse = mapPerson(person);
        return new ResponseEntity<>(ResponsePlatformApi.builder().error("string")
                .timestamp(new Date().getTime()).data(personResponse)
                .build(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponsePlatformApi> editCurrentUser(HttpSession session, PersonEditBody personEditBody) {
        Person fromPerson = getCurrentUser();
        Person toPerson = personsMapper.requestPersonToPerson(personEditBody);
        toPerson.setId(fromPerson.getId());
        toPerson.setRegDate(fromPerson.getRegDate());
        toPerson.setEmail(fromPerson.getEmail());
        toPerson.setLastOnlineTime(dataMapper.asLocalDateTime(new Date().getTime()));
        toPerson.setPassword(fromPerson.getPassword());
        personRepository.save(toPerson);
        ResponsePlatformApi api = ResponsePlatformApi.builder()
                .error("Ok")
                .timestamp(new Date().getTime())
                .data(personsMapper.personToPersonResponse(toPerson)).build();
        return new ResponseEntity<>(api, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponsePlatformApi> deleteCurrentUser(HttpSession session) {
        Person person = getCurrentUser();
        person.setDeleted(true);
        personRepository.save(person);
        ReportApi reportApi = new ReportApi();
        reportApi.setMessage("Ok");
        ResponsePlatformApi api = ResponsePlatformApi.builder()
                .error("Ok")
                .timestamp(new Date().getTime())
                .data(reportApi).build();
        return new ResponseEntity<>(api, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponsePlatformApi> getUserById(HttpSession session, int id) {
        Person person = getPersonById(id);
        ResponsePlatformApi psApi = ResponsePlatformApi.builder()
                .error("Ok")
                .timestamp(new Date().getTime())
                .data(personsMapper.personToPersonResponse(person))
                .build();
        return new ResponseEntity<>(psApi, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponsePlatformApi> getPersonsWallPostsByUserId(HttpSession session, int id,
                                                                           int offset, int itemPerPage) {
        List<Post> posts = postRepository.findAllByAuthorId(id);
        if (posts.isEmpty()) {
            throw new EntityNotFoundException("No entries for this id = " + id);
        }
        List<Post> postList = posts.stream().skip(offset).limit(itemPerPage).collect(Collectors.toList());
        List<PostResponse> api = postMapper.postToPostResponse(postList);
        ResponsePlatformApi platformApi = ResponsePlatformApi.builder()
                .error("Ok")
                .timestamp(new Date().getTime())
                .total(posts.size())
                .offset(offset)
                .perPage(itemPerPage)
                .data(api).build();
        return new ResponseEntity<>(platformApi, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponsePlatformApi> addPostToUsersWall(HttpSession session, int id, int publishDate,
                                                                  PostRequestBody postRequestBody) {
        Person person = getPersonById(id);
        Post post = postMapper.requestPostToPost(postRequestBody);
        post.setAuthor(person);
        post.setTime(dataMapper.asLocalDate(publishDate));
        postRepository.save(post);
        PostResponse postResponse = postMapper.postToPostResponse(post);
        ResponsePlatformApi platformApi = ResponsePlatformApi.builder()
                .error("Ok")
                .timestamp(new Date().getTime())
                .data(postResponse).build();
        return new ResponseEntity<>(platformApi, HttpStatus.OK);
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

    // Заглушка
    private Person getCurrentUser() {
        return getPersonById(2);
    }

    private Person getPersonById(int id) {
        return personRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with this ID not found."));
    }

}
