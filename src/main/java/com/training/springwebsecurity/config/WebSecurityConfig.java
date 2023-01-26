package com.training.springwebsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	@Bean
	public UserDetailsService userDetailsService() {
		InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();
		UserDetails user = User.withUsername("admin").password("admin").authorities("ADMIN").build();
		userDetailsService.createUser(user);
		return userDetailsService;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers("/").permitAll()  // it allow for anyuser
			.antMatchers("/admin").authenticated() // check authentication 
			.antMatchers("/admin").hasAnyRole("ADMIN")  // check authorization 
			.and()
			.formLogin()
			.loginPage("/login").permitAll()
	        .and()
	        .logout()
	        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	        .and()
	        .rememberMe()
	        .key("rem-me-key") // it is name of checkbox at login page
	        .rememberMeParameter("remember") // it is name of the cookie  
	        .rememberMeCookieName("rememberlogin")
	        .tokenValiditySeconds(40) ;// remember for number of seconds  

		}
}