package com.example.security1.config.oauth.provider;

/**
 * Created by cadqe13@gmail.com on 2022-04-14
 * Blog : https://velog.io/@donsco
 * GitHub : https://github.com/wlwlsus
 */
public interface OAuth2UserInfo {
	String getProviderId();

	String getProvider();

	String getEmail();

	String getName();
}
