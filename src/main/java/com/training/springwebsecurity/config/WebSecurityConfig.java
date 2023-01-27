package com.training.springwebsecurity.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

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
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http//.csrf().disable()
			.authorizeRequests()
				.antMatchers("/","/csrf*").permitAll()  // it allow for anyuser
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