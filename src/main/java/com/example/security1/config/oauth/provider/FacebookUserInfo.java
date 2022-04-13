package com.example.security1.config.oauth.provider;

import java.util.Map;

/**
 * Created by cadqe13@gmail.com on 2022-04-14
 * Blog : https://velog.io/@donsco
 * GitHub : https://github.com/wlwlsus
 */
public class FacebookUserInfo implements OAuth2UserInfo{
	private final Map<String, Object> attributes; //OAuth2 getAttributes()

	public FacebookUserInfo(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	@Override
	public String getProviderId() {
		return (String) attributes.get("id");
	}

	@Override
	public String getProvider() {
		return "facebook";
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
