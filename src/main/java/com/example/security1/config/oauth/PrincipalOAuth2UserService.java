package com.example.security1.config.oauth;

import com.example.security1.config.auth.PrincipalDetails;
import com.example.security1.config.oauth.provider.FacebookUserInfo;
import com.example.security1.config.oauth.provider.GoogleUserInfo;
import com.example.security1.config.oauth.provider.NaverUserInfo;
import com.example.security1.config.oauth.provider.OAuth2UserInfo;
import com.example.security1.model.User;
import com.example.security1.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by cadqe13@gmail.com on 2022-04-13
 * Blog : https://velog.io/@donsco
 * GitHub : https://github.com/wlwlsus
 */
@Service
public class PrincipalOAuth2UserService extends DefaultOAuth2UserService {

	private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	private final UserRepository userRepository;

	public PrincipalOAuth2UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	// 구글로 부터 받은 userRequest 데이터에 대해서 후처리 되는 함수
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

		System.out.println("getClientRegistration: " + userRequest.getClientRegistration()); //registrationid로 어떤  oauth로 로그인했는지 확인
		System.out.println("getTokenValue: " + userRequest.getAccessToken().getTokenValue());

		OAuth2User oAuth2User = super.loadUser(userRequest);


		// 구글 로그인 버튼 클릭 -> 로그인창 -> 로그인 완료 -> code 리턴(OAuth-client 라이브러리가 받는다) -> accessToken 요청
		// 그렇게 userRequest 정보를 받게 된다. -> loadUser 함수를 통해 회원 프로필 받는다.
		System.out.println("호출1번 getAttributes: " + oAuth2User.getAttributes());

		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		OAuth2UserInfo oAuth2UserInfo = null;

		if (registrationId.equals("google")) {
			System.out.println("구글 로그인 요청");
			oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
		} else if (registrationId.equals("facebook")) {
			System.out.println("페이스북 로그인 요청");
			oAuth2UserInfo = new FacebookUserInfo(oAuth2User.getAttributes());
		} else if (registrationId.equals("naver")) {
			System.out.println("네이버 로그인 요청");
			oAuth2UserInfo = new NaverUserInfo((Map<String,Object>) oAuth2User.getAttributes().get("response"));
		} else {
			System.out.println("구글과 페이스북, 네이버가 아닙니다.");
		}


		String provider = oAuth2UserInfo.getProvider();
		String providerId = oAuth2UserInfo.getProviderId();
		String username = provider + "_" + providerId;
		String password = bCryptPasswordEncoder.encode("겟인데어");
		String email = oAuth2UserInfo.getEmail();
		String role = "ROLE_USER";


		User userEntity = userRepository.findByUsername(username);

		if (userEntity == null) {
			userEntity = User.builder()
							.username(username)
							.password(password)
							.email(email)
							.role(role)
							.provider(provider)
							.providerId(providerId)
							.build();

			userRepository.save(userEntity);
		} else {
			System.out.println("로그인을 이미 한 적이 있습니다. 자동 회원가입 되었습니다.");
		}

		System.out.println("PrincipalOAuth2UserService - loadUser : " + oAuth2User);
		System.out.println("PrincipalOAuth2UserService - save : " + userEntity);


		return new PrincipalDetails(userEntity, oAuth2User.getAttributes());

	}
}
