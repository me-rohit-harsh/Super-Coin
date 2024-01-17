package com.supercoin.coin.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.supercoin.coin.model.User;
import com.supercoin.coin.repository.UserRepository;
import com.supercoin.coin.service.UserServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class registerController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserServiceImpl userServiceImpl;

	@GetMapping("/register")
	public String createUserForm(HttpServletRequest request,
			@RequestParam(value = "sponserId", required = false) Integer sponserId, HttpSession session) {

		if (sponserId != null) {
			System.err.println(sponserId);
			session.setAttribute("sponserName", userServiceImpl.getUserName(sponserId));
		} else
			session.setAttribute("sponserName", null);
			System.err.println("User extaced but have no sponserId");
		return "register";
	}

	@PostMapping("/user")
	public String saveUser(@ModelAttribute("user") User user, HttpSession session) {
		UUID uuid = UUID.randomUUID();
		String secretCode = uuid.toString().replaceAll("[^a-zA-Z0-9]", "").substring(0, 6).toUpperCase();
		user.setCode(secretCode);
		userRepository.save(user);
		System.err.println(user);
		session.setAttribute("userEmail", user.getEmail());
		System.err.println(
				"User ID: " + user.getId() + " || Secrete Code: " + user.getCode() + " || Email: " + user.getEmail());
		session.setAttribute("verification", true);
		return "redirect:/login?success";
	}

}
