package com.training.springwebsecurity.config;

import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
	@Bean
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).addDefaultScripts().build();
	}

	@Bean
	public UserDetailsService userDetailsService(DataSource dataSource) {
		String DEF_USERS_BY_USERNAME_QUERY = "select username, password, enabled from users where username = ?";
		String DEF_AUTHORITIES_BY_USERNAME_QUERY = "select username, authority from authorities where username = ?";

		JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
		//userDetailsManager.createUser(createUser());

		userDetailsManager.setUsersByUsernameQuery(JdbcDaoImpl.DEF_USERS_BY_USERNAME_QUERY);
		userDetailsManager.setAuthoritiesByUsernameQuery(JdbcDaoImpl.DEF_AUTHORITIES_BY_USERNAME_QUERY);

		return userDetailsManager;
	}

	private UserDetails createUser() {
		UserDetails user = User.withUsername("user1").password("user1").roles("USER1").build();
		return user;
	}
	
	@Bean
	MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
	    return new MvcRequestMatcher.Builder(introspector);
	}
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
        http.cors(cors -> cors.disable())
        	.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests((authz) -> authz
            		.requestMatchers(mvc.pattern("/"),mvc.pattern("/login"),mvc.pattern("/csrf"),mvc.pattern("/csrfSubmit")).permitAll()
            		.requestMatchers(mvc.pattern("/admin")).hasRole("ADMIN")
            )
	        .formLogin((formLogin) -> {System.out.println("http form operation performed");
	        		formLogin.loginPage("/login");
	        })
	        .logout((logout) -> {
	        	logout.logoutRequestMatcher(mvc.pattern("/logout"));
	        	logout.logoutSuccessUrl("/");
	         })
	        .rememberMe( rememberMe -> {
	        	rememberMe.key("rem-me-key");
	        	rememberMe.rememberMeParameter("remember"); // it is name of checkbox at login page   
	        	rememberMe.rememberMeCookieName("rememberlogin"); // it is name of the cookie
	        	rememberMe.tokenValiditySeconds(40) ;// remember for number of seconds  
	        	
	        });
        return http.build();
    }
}