package com.example.security1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author : cadqe13@gmail.com
 * date : 2022-04-08
 * description :
 */
@Controller
@RestController
public class IndexController {

	// localhost:8080
	@GetMapping({"", "/"})
	public String index() {
		return "index";
	}

	@GetMapping("/user")
	public String user() {
		return "user";
	}

	@GetMapping("/admin")
	public String admin() {
		return "admin";
	}

	@GetMapping("/manager")
	public String manager() {
		return "manager";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/join")
	public String join() {
		return "join";
	}

	@GetMapping("/joinProc")
	public String joinProc() {
		return "회원가입 완료!";
	}

}
