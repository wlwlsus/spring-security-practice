package com.example.security1.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * author : cadqe13@gmail.com
 * date : 2022-04-08
 * description :
 */
@Configuration
@EnableWebSecurity //Spring Security Filter 가 Spring Filter Chain 에 등록된다.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
						.antMatchers("/user/**").authenticated()
						.antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
						.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
						.anyRequest().permitAll()
						.and()
						.formLogin()
						.loginPage("/login");
	}
}
