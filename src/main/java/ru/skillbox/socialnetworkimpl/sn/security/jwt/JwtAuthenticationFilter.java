package ru.skillbox.socialnetworkimpl.sn.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.skillbox.socialnetworkimpl.sn.api.responses.PersonResponse;
import ru.skillbox.socialnetworkimpl.sn.api.responses.ResponsePlatformApi;
import ru.skillbox.socialnetworkimpl.sn.repositories.PersonRepository;
import ru.skillbox.socialnetworkimpl.sn.security.SecurityConstants;
import ru.skillbox.socialnetworkimpl.sn.domain.Person;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.skillbox.socialnetworkimpl.sn.services.mappers.PersonsMapper;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final PersonRepository personRepository;
    private final PersonsMapper personsMapper;
    private final ObjectMapper mapper;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, PersonRepository personRepository, PersonsMapper personsMapper, ObjectMapper mapper) {
        this.authenticationManager = authenticationManager;
        this.personRepository = personRepository;
        this.personsMapper = personsMapper;
        this.mapper = mapper;
        this.setFilterProcessesUrl(SecurityConstants.API_LOGIN_URL);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {

            Person cred = new ObjectMapper()
                    .readValue(request.getInputStream(), Person.class);

            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    cred.getEmail(),
                    cred.getPassword(),
                    new ArrayList<>())
            );

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        String user = ((User) authResult.getPrincipal()).getUsername();

        PersonResponse personResponse = personsMapper.personToPersonResponse(personRepository.findByEmail(user));

        personResponse.setToken(SecurityConstants.PREFIX +
                JwtProvider.createToken(user, new ArrayList<>()));

        try {
            PrintWriter writer = response.getWriter();
            mapper.writeValue(writer, new ResponsePlatformApi("", 0, 0, 0, personResponse));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
