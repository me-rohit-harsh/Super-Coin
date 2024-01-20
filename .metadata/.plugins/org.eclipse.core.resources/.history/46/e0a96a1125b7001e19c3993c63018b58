package com.supercoin.coin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class logoutController {

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		// Invalidate the user's session
		session.invalidate();
		System.out.println("Session invalidated succesfully for user ");
		return "redirect:login";
	}
}
