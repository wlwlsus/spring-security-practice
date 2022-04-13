package com.example.security1.config.oauth.provider;

import java.util.Map;

/**
 * Created by cadqe13@gmail.com on 2022-04-14
 * Blog : https://velog.io/@donsco
 * GitHub : https://github.com/wlwlsus
 */
public class GoogleUserInfo implements OAuth2UserInfo{

	private final Map<String, Object> attributes; //OAuth2 getAttributes()

	public GoogleUserInfo(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	@Override
	public String getProviderId() {
		return (String) attributes.get("sub");
	}

	@Override
	public String getProvider() {
		return "google";
	}

	@Override
	public String getEmail() {
		return (String) attributes.get("email");
	}

	@Override
	public String getName() {
		return (String) attributes.get("name");
	}
}
