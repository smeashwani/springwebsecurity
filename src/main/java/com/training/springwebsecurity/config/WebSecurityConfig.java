package com.training.springwebsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth
			.inMemoryAuthentication()
				.withUser("user").password(passwordEncoder().encode("user")).roles("USER")
				.and()
				.withUser("admin").password(passwordEncoder().encode("admin")).roles("ADMIN");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
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
	        .key("rem-me-key") 
	        .rememberMeParameter("remember") // it is name of checkbox at login page   
	        .rememberMeCookieName("rememberlogin") // it is name of the cookie
	        .tokenValiditySeconds(40) ;// remember for number of seconds  

		}
}