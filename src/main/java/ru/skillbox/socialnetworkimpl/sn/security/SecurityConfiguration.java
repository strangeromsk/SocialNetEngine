package ru.skillbox.socialnetworkimpl.sn.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private String jwtSecret;
    private String tokenHeader;
    private String tokenPrefix;
    private String tokenType;
    private String tokenIssuer;
    private String tokenAudience;
    private String authLoginUrl;

    public SecurityConfiguration(@Value("${jwt.secret}") String jwtSecret,
                                   @Value("${jwt.token-header}") String tokenHeader,
                                   @Value("${jwt.token-prefix}") String tokenPrefix,
                                   @Value("${jwt.token-type}") String tokenType,
                                   @Value("${jwt.token-issuer}") String tokenIssuer,
                                   @Value("${jwt.token-audience}") String tokenAudience,
                                   @Value("${jwt.auth-login-url}") String authLoginUrl) {
        this.jwtSecret = jwtSecret;
        this.tokenHeader = tokenHeader;
        this.tokenPrefix = tokenPrefix;
        this.tokenType = tokenType;
        this.tokenIssuer = tokenIssuer;
        this.tokenAudience =tokenAudience;
        this.authLoginUrl = authLoginUrl;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/public").permitAll()
                .anyRequest().authenticated()

//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .loginProcessingUrl("/authenticate")
//                .usernameParameter("user")
//                .passwordParameter("password")
//                .and()
//                .logout()
//                .logoutSuccessUrl("/")

                .and()
                .addFilter(new JwtAuthenticationFilter(jwtSecret, tokenHeader, tokenPrefix, tokenType, tokenIssuer, tokenAudience, authLoginUrl, authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(jwtSecret, tokenHeader, tokenPrefix, authenticationManager()))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user")
                .password(passwordEncoder().encode("password"))
                .authorities("ROLE_USER");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());

        return source;
    }
}
