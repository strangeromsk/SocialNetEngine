package ru.skillbox.socialnetworkimpl.sn.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import ru.skillbox.socialnetworkimpl.sn.repositories.PersonRepository;
import ru.skillbox.socialnetworkimpl.sn.security.jwt.JwtAuthenticationFilter;
import ru.skillbox.socialnetworkimpl.sn.security.jwt.JwtAuthorizationFilter;
import ru.skillbox.socialnetworkimpl.sn.security.jwt.JwtAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import ru.skillbox.socialnetworkimpl.sn.services.mappers.PersonsMapper;
import javax.servlet.Filter;
import java.util.Arrays;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl personService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonsMapper personsMapper;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private LogoutSuccessHandler logoutHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, SecurityConstants.API_LOGIN_URL).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), personRepository, personsMapper, mapper))
                .addFilter(new JwtAuthorizationFilter(authenticationManager()))
                .exceptionHandling().authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .logout().logoutUrl(SecurityConstants.API_LOGOUT_URL)
                .logoutSuccessHandler(logoutHandler);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(personService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @EventListener
    public void handle(ContextRefreshedEvent event) {
        FilterChainProxy proxy = getApplicationContext().getBean(FilterChainProxy.class);
        for (Filter f : proxy.getFilters("/")) {
            if (f instanceof FilterSecurityInterceptor) {
                ((FilterSecurityInterceptor) f).setPublishAuthorizationSuccess(true);
            }
        }
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8080"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "HEAD"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}