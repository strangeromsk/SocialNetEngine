package ru.skillbox.socialnetworkimpl.sn.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.skillbox.socialnetworkimpl.sn.security.SecurityConstants;
import ru.skillbox.socialnetworkimpl.sn.domain.Person;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
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
        String token = JwtProvider.createToken(((User) authResult.getPrincipal()).getUsername(), new ArrayList<>());
        response.addHeader(SecurityConstants.HEADER, SecurityConstants.PREFIX + token);
    }
}
