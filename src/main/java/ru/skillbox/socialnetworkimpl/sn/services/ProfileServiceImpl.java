package ru.skillbox.socialnetworkimpl.sn.services;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetworkimpl.sn.api.requests.PersonRequest;
import ru.skillbox.socialnetworkimpl.sn.api.requests.PostRequest;
import ru.skillbox.socialnetworkimpl.sn.api.responses.*;
import ru.skillbox.socialnetworkimpl.sn.domain.Country;
import ru.skillbox.socialnetworkimpl.sn.domain.Person;
import ru.skillbox.socialnetworkimpl.sn.domain.Post;
import ru.skillbox.socialnetworkimpl.sn.repositories.CityRepository;
import ru.skillbox.socialnetworkimpl.sn.repositories.CountryRepository;
import ru.skillbox.socialnetworkimpl.sn.repositories.PersonRepository;
import ru.skillbox.socialnetworkimpl.sn.repositories.PostRepository;
import ru.skillbox.socialnetworkimpl.sn.services.interfaces.ProfileService;
import ru.skillbox.socialnetworkimpl.sn.services.mappers.DataMapper;
import ru.skillbox.socialnetworkimpl.sn.services.mappers.PersonMapper;
import ru.skillbox.socialnetworkimpl.sn.services.mappers.PersonsMapper;
import ru.skillbox.socialnetworkimpl.sn.services.mappers.PostMapper;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
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
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private CountryRepository countryRepository;

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(PRIVATE);

        Converter<LocalDate, Long> toLongDate = new AbstractConverter<LocalDate, Long>() {
            protected Long convert(LocalDate source) {
                return source != null ? source.atStartOfDay(ZoneOffset.UTC).toEpochSecond() * 1000 : 0;
            }
        };

        Converter<Long, LocalDate> toLocalDate = new AbstractConverter<Long, LocalDate>() {
            protected LocalDate convert(Long source) {
                return LocalDate.ofEpochDay(source/86_400_000);
            }
        };

        Converter<LocalDateTime, Long> toLongTime = new AbstractConverter<LocalDateTime, Long>() {
            protected Long convert(LocalDateTime source) {
                return source != null ? source.atOffset(ZoneOffset.UTC).toEpochSecond() * 1000 : 0;
            }
        };

        mapper.addConverter(toLocalDate);
        mapper.addConverter(toLongDate);
        mapper.addConverter(toLongTime);
        return mapper;
    }


    public ru.skillbox.socialnetworkimpl.sn.api.responses.PersonResponse mapPerson(Person person) {
        return personMapper.toDto(person);
    }

    @Override
    public ResponseEntity<ResponsePlatformApi> getCurrentUser(HttpSession session) {
        Person person = getCurrentUser();
        PersonResponse personResponse = mapPerson(person);
        Country country = countryRepository.getOne(person.getTown().getCountryId());
        CountryResponse countryResponse = new CountryResponse(country.getId(), country.getTitle());
        personResponse.setCountry(countryResponse);
        return new ResponseEntity<>(ResponsePlatformApi.builder().error("string")
                .timestamp(new Date().getTime()).data(personResponse)
                .build(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponsePlatformApi> editCurrentUser(HttpSession session, PersonRequest personRequest) {
        Person fromPerson = getCurrentUser();
        personRequest.setTown(cityRepository.getOne(personRequest.getCity()));
        Person toPerson = personsMapper.requestPersonToPerson(personRequest);
        toPerson.setId(fromPerson.getId());
        toPerson.setRegDate(fromPerson.getRegDate());
        toPerson.setEmail(fromPerson.getEmail());
        toPerson.setLastOnlineTime(dataMapper.asLocalDateTime(new Date().getTime()));
        toPerson.setPassword(fromPerson.getPassword());

        Country country = countryRepository.getOne(personRequest.getCountryId());
        CountryResponse countryResponse = new CountryResponse(country.getId(), country.getTitle());
        PersonResponse ps = personsMapper.personToPersonResponse(toPerson);
        ps.setCountry(countryResponse);

        personRepository.save(toPerson);
        ResponsePlatformApi api = ResponsePlatformApi.builder()
                .error("Ok")
                .timestamp(new Date().getTime())
                .data(ps).build();
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
        Country country = countryRepository.getOne(person.getTown().getCountryId());
        CountryResponse countryResponse = new CountryResponse(country.getId(), country.getTitle());
        ru.skillbox.socialnetworkimpl.sn.api.responses.PersonResponse ps = personsMapper.personToPersonResponse(person);
        ps.setCountry(countryResponse);
        ResponsePlatformApi psApi = ResponsePlatformApi.builder()
                .error("Ok")
                .timestamp(new Date().getTime())
                .data(ps)
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
        if (postList.isEmpty()) {
            throw new EntityNotFoundException("No entries for offset and itemPerPage");
        }
        Country country = countryRepository.getOne(postList.get(0).getAuthor().getTown().getCountryId());
        CountryResponse countryResponse = new CountryResponse(country.getId(), country.getTitle());
        List<PostResponse> api = postMapper.postToPostResponse(postList);
        api.stream().forEach(x -> x.getAuthor().setCountry(countryResponse));
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
    public ResponseEntity<ResponsePlatformApi> addPostToUsersWall(HttpSession session, int id, long publishDate,
                                                                  PostRequest postRequest) {
        Person person = getPersonById(id);
        Post post = postMapper.requestPostToPost(postRequest);
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
        //Заглушка на проверку пользователя
        boolean isAuthorized = true;
        if (!isAuthorized)
            return accountService.getUserInvalidResponse();
        List<Person> list = personRepository.findPersons(firstName, lastName, ageFrom, ageTo, cityId);

        List<ru.skillbox.socialnetworkimpl.sn.api.responses.PersonResponse> personResponseList = list.stream()
                .map(this::mapPerson)
                .collect(Collectors.toCollection(ArrayList::new));

        Country country = countryRepository.getOne(countryId);
        CountryResponse countryResponse = new CountryResponse(country.getId(), country.getTitle());
        personResponseList.stream().forEach(x -> x.setCountry(countryResponse));
        return new ResponseEntity<>(ResponsePlatformApi.builder().error("Ok")
                .timestamp(new Date().getTime()).total(personResponseList.size()).offset(offset)
                .perPage(itemPerPage).data(personResponseList)
                .build(), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<ResponsePlatformApi> blockUserById(HttpSession session, int id) {
        personRepository.blockUserById(id);
        // Заглушка на проверку пользователя
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
