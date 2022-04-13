package com.example.security1.config.auth;

import com.example.security1.model.User;
import com.example.security1.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by cadqe13@gmail.com on 2022-04-13
 * Blog : https://velog.io/@donsco
 * GitHub : https://github.com/wlwlsus
 */

@Service
public class PrincipalDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	public PrincipalDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if(user == null) {
			return null;
		}
		return new PrincipalDetails(user);
	}

}