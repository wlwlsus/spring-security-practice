package com.example.security1.config.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

/**
 * Created by cadqe13@gmail.com on 2022-04-13
 * Blog : https://velog.io/@donsco
 * GitHub : https://github.com/wlwlsus
 */
@Service
public class PrincipalOAuth2UserService extends DefaultOAuth2UserService {

	// 구글로 부터 받은 userRequest 데이터에 대해서 후처리 되는 함수
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

		System.out.println("getClientRegistration: " + userRequest.getClientRegistration()); //registrationid로 어떤  oauth로 로그인했는지 확인
		System.out.println("getTokenValue: " + userRequest.getAccessToken().getTokenValue());

		// 구글 로그인 버튼 클릭 -> 로그인창 -> 로그인 완료 -> code 리턴(OAuth-client 라이브러리가 받는다) -> accessToken 요청
		// 그렇게 userRequest 정보를 받게 된다. -> loadUser 함수를 통해 회원 프로필 받는다.
		System.out.println("getAttributes: " + super.loadUser(userRequest).getAttributes());

		OAuth2User oAuth2User = super.loadUser(userRequest);
		return super.loadUser(userRequest);
	}
}
