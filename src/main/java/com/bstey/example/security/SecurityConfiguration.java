package com.bstey.example.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Configures HTTP Basic authentication
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomBasicAuthenticationEntryPoint authEntryPoint;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("toto").password("titi").roles("USER");
	}

	/**
	 *  See https://docs.spring.io/spring-security/site/docs/current/reference/html/jc.html
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off

		http
			.csrf().disable()
			.authorizeRequests()
				.antMatchers("/example/**").authenticated()
				//.antMatchers("/example/v2/**").hasRole("USER")
				//.anyRequest().authenticated()
				.and()
			.httpBasic().authenticationEntryPoint(authEntryPoint);

		// @formatter:on
	}

	 /* To allow Pre-flight [OPTIONS] request from browser */
	 @Override
	 public void configure(WebSecurity web) throws Exception {
		 web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
	 }

}
