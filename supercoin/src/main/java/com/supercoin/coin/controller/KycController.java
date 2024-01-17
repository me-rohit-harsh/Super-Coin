package com.supercoin.coin.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.supercoin.coin.model.KYC;
import com.supercoin.coin.model.User;
import com.supercoin.coin.repository.KycRepository;
import com.supercoin.coin.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class KycController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private KycRepository kycRepository;

	@GetMapping("/kyc")
	public String kyc(HttpSession session, Model model) {

		Integer userId = (Integer) session.getAttribute("userid");
		Boolean auth = (Boolean) session.getAttribute("authentication");

		if (userId != null && Boolean.TRUE.equals(auth)) {
			Optional<User> optionalUser = userRepository.findById(userId);
			if (optionalUser.isPresent()) {
				User user = optionalUser.get();
				model.addAttribute("user", user);
				return "kyc";
			}
		}
		session.setAttribute("access", false);
		return "redirect:login?accessDenied";
	}

	@PostMapping("/kycUpdate")
	public String kycUpdate(@ModelAttribute("KYC") KYC kyc, HttpSession session, Model model) {
		Integer userId = (Integer) session.getAttribute("userid");
		Boolean auth = (Boolean) session.getAttribute("authentication");

		if (userId != null && Boolean.TRUE.equals(auth)) {
			Optional<User> optionalUser = userRepository.findById(userId);
			if (optionalUser.isPresent()) {
				User user = optionalUser.get();

				kyc.setName(user.getName());
				// Save the new KYC object with other fields populated automatically
				kycRepository.save(kyc);

				System.err.println(kyc);
				// Set a success message and redirect
				session.setAttribute("kycMsg",
						"Success!! KYC form has been submitted! It will take some time to verify. Thank you :) ");
				return "redirect:kyc?submitted";
			}

			session.setAttribute("notSubmitted", true);
			return "redirect:kyc?notSubmitted";
		}

		session.setAttribute("access", false);
		return "redirect:login?accessDenied";
	}

}
