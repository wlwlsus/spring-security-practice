package com.example.security1.config;

import com.example.security1.config.oauth.PrincipalOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * author : cadqe13@gmail.com
 * date : 2022-04-08
 * description :
 * 1. 코드받기
 */
@Configuration
@EnableWebSecurity //Spring Security Filter 가 Spring Filter Chain 에 등록된다.
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true) // 특정 주소 접근시 권한 및 인증을 위한 어노테이션 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final PrincipalOAuth2UserService principalOAuth2UserService;

	public SecurityConfig(PrincipalOAuth2UserService principalOAuth2UserService) {
		this.principalOAuth2UserService = principalOAuth2UserService;
	}


	@Bean
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
						.antMatchers("/user/**").authenticated()
						.antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
						.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
						.anyRequest().permitAll()
						.and()
						.formLogin()
						.loginPage("/loginForm")
						.loginProcessingUrl("/login")
						.defaultSuccessUrl("/")
						.and()
						.oauth2Login()
						.loginPage("/loginForm")
						.userInfoEndpoint()
						.userService(principalOAuth2UserService); // 구글 로그인이 완료된 뒤 후처리가 필요하다. TIP. 코드x(액세스 토큰 + 사용자 프로필 정보)
	}
}


