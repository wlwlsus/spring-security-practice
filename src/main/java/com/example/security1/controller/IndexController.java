package com.example.security1.controller;

import com.example.security1.config.auth.PrincipalDetails;
import com.example.security1.model.User;
import com.example.security1.repository.UserRepository;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * author : cadqe13@gmail.com
 * date : 2022-04-08
 * description :
 */
@Controller
public class IndexController {

	private final UserRepository userRepository;

	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping("/test/login")
	@ResponseBody
	//AuthenticationPrincipal 세션정보 접근가능
	public String testLogin(Authentication authentication, @AuthenticationPrincipal PrincipalDetails userDetails) { // Authentication >> 여기서 DI 진행
		System.out.println("/test/login ============");

		PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
		System.out.println("authentication: " + principalDetails.getUser());

		System.out.println("userDetails: " + userDetails.getUser());
		return "세션 정보 확인";
	}

	@GetMapping("/test/oauth/login")
	@ResponseBody
	public String testOAuthLogin(Authentication authentication, @AuthenticationPrincipal OAuth2User oAuth) {
		System.out.println("/test/oauth/login ============");

		OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
		System.out.println("authentication: " + oAuth2User.getAttributes());
		System.out.println("oauth2user: " + oAuth.getAttributes());
		return "OAuth 세션 정보 확인";
	}

	public IndexController(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	// localhost:8080
	@GetMapping({"", "/"})
	public String index() {
		return "index";
	}

	@GetMapping("/user")
	@ResponseBody
	public String user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
		System.out.println("principledatails: " + principalDetails.getUser());
		return "user";
	}

	@GetMapping("/admin")
	@ResponseBody
	public String admin() {
		return "admin";
	}

	//@PostAuthorize("hasRole('ROLE_MANAGER')")
	//@PreAuthorize("hasRole('ROLE_MANAGER')")
	@Secured("ROLE_MANAGER")
	@GetMapping("/manager")
	@ResponseBody
	public String manager() {
		return "manager";
	}

	@GetMapping("/loginForm")
	public String loginForm() {
		return "loginForm";
	}

	@GetMapping("/joinForm")
	public String joinForm() {
		return "joinForm";
	}

	@PostMapping("/join")
	public String joinForm(User user) {
		user.setRole("ROLE_USER");
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		user.setPassword(encPassword);
		System.out.println(user);

		userRepository.save(user);

		System.out.println(userRepository.findAll());
		return "redirect:/loginForm";
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/info")
	@ResponseBody
	public String info() {
		return "개인 정보";
	}

	@PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
	@GetMapping("/data")
	@ResponseBody
	public String data() {
		return "데이터 정보";
	}
}
