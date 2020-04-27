package ru.skillbox.socialnetworkimpl.sn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.skillbox.socialnetworkimpl.sn.jwtsecurity.JwtRequestFilter;

import javax.sql.DataSource;

@SpringBootApplication
public class JavaProSocialNetworkStudyGroup5Application {

    public static void main(String[] args) {
        SpringApplication.run(JavaProSocialNetworkStudyGroup5Application.class, args);
    }

	@Configuration
	public static class DataSourceConfiguration {

		@Value("${spring.datasource.url}")
		String url;
		@Value("${spring.datasource.username}")
		String username;
		@Value("${spring.datasource.password}")
		String password;

		@Bean
		public DataSource getDataSource() {
			return DataSourceBuilder.create()
					.url(url)
					.username(username)
					.password(password)
					.build();
		}
	}

	@EnableWebSecurity
	public static class SecurityConfiguration extends WebSecurityConfigurerAdapter {

		@Autowired
		private JwtRequestFilter jwtRequestFilter;

		@Autowired
		private DataSource dataSource;

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.jdbcAuthentication()
					.dataSource(dataSource)
					.usersByUsernameQuery("select e_mail, password from person");
		}

		@Override
		@Bean
		public AuthenticationManager authenticationManagerBean() throws Exception {
			return super.authenticationManagerBean();
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
					.csrf().disable()
					.authorizeRequests()
					.antMatchers("/api/v1/auth/login")
					.permitAll()
					.anyRequest()
					.authenticated()
					.and()
					.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					.and()
					.exceptionHandling()
					.and()
					.logout()
					.logoutUrl("/api/v1/auth/logout")
					.logoutSuccessUrl("/api/v1/auth/login")
					.permitAll();

			http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		}
	}
}
