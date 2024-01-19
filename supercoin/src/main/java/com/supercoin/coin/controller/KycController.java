package com.supercoin.coin.controller;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.supercoin.coin.model.Ticket;
import com.supercoin.coin.model.KYC;
import com.supercoin.coin.model.User;
import com.supercoin.coin.repository.KycRepository;
import com.supercoin.coin.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

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
				KYC kyc = user.getKyc();
				System.err.println(kyc);
				model.addAttribute("kyc", kyc);
				return "kyc";
			}
		}
		session.setAttribute("access", false);
		return "redirect:login?accessDenied";
	}

	@PostMapping("/kycUpdate")
	public String kycUpdate(@ModelAttribute("KYC") KYC kyc, HttpSession session, Model model,
			@RequestParam("frontFile") MultipartFile frontFile, @RequestParam("backFile") MultipartFile backFile)
			throws IOException {
		Integer userId = (Integer) session.getAttribute("userid");
		Boolean auth = (Boolean) session.getAttribute("authentication");

		if (userId != null && Boolean.TRUE.equals(auth)) {
			Optional<User> optionalUser = userRepository.findById(userId);
			if (optionalUser.isPresent()) {
				User user = optionalUser.get();

				// Getting the kyc object of user
				KYC userKyc = user.getKyc();

				// Save the new KYC object with other fields populated automatically
				userKyc.setName(user.getName());
				if (!frontFile.isEmpty()) {
					kyc.setFrontImage(frontFile.getBytes());
				}

				if (!backFile.isEmpty()) {
					kyc.setBackImage(backFile.getBytes());
				}
				userKyc.setBackImage(kyc.getBackImage());
				userKyc.setFrontImage(kyc.getFrontImage());
				userKyc.setAge(kyc.getAge());
				userKyc.setDocNumber(kyc.getDocNumber());
				userKyc.setDocType(kyc.getDocType());
				userKyc.setStatus("Pending");
				kycRepository.save(userKyc);
				user.setKyc(userKyc);
				userRepository.save(user);
				System.err.println(userKyc);
				// Set a success message and redirect
				session.setAttribute("kycMsg",
						"KYC Submitted for id " + user.getId() + " Thank you :) ");
				return "redirect:kyc?submitted";
			}

			session.setAttribute("notSubmitted", true);
			return "redirect:kyc?notSubmitted";
		}

		session.setAttribute("access", false);
		return "redirect:login?accessDenied";
	}

}
