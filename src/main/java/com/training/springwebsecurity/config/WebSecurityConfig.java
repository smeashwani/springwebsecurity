package com.training.springwebsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
 	public UserDetailsService userDetailsService() {
		
 		UserDetails user1 = User.withUsername("user")
 			.password(passwordEncoder().encode("user"))
 			.roles("USER")
 			.build();
 		
 		UserDetails user2 = User.withUsername("admin")
 	 			.password(passwordEncoder().encode("admin"))
 	 			.roles("ADMIN")
 	 			.build();
 		return new InMemoryUserDetailsManager(user1, user2);
 	}
	
	@Bean
	MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
	    return new MvcRequestMatcher.Builder(introspector);
	}
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
        http
            .authorizeHttpRequests((authz) -> authz
            		.requestMatchers(mvc.pattern("/")).permitAll()
            		.requestMatchers(mvc.pattern("/admin")).hasRole("ADMIN")
            )
            .formLogin();
        return http.build();
    }
}