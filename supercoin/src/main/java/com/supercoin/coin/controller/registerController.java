package com.supercoin.coin.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.supercoin.coin.model.KYC;
import com.supercoin.coin.model.User;
import com.supercoin.coin.repository.KycRepository;
import com.supercoin.coin.repository.UserRepository;
import com.supercoin.coin.service.EmailService;
import com.supercoin.coin.service.UserServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Controller
@RequestMapping("/register")
public class registerController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserServiceImpl userServiceImpl;
	@Autowired
	private KycRepository kycRepository;
	  @Autowired
	    private EmailService emailService;
	@GetMapping
	@Transactional
	public String createUserForm(HttpServletRequest request,
			@RequestParam(value = "sponserId", required = false) Integer sponserId, HttpSession session, Model model) {
		model.addAttribute("user", new User());
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
		KYC kyc = new KYC();
		user.setKyc(kyc);
		kycRepository.save(kyc);
		userRepository.save(user);
		
		  // Generate email content
        String loginLink = "http://localhost:3310/login";
        Integer userID = user.getId();
        String Code = user.getCode();
        Integer sponsorID = user.getSponserId();

        String content = "Login link: <a href='" + loginLink + "'>" + loginLink + "</a><br>" +
                        "User ID: " + userID + "<br>" +
                        "Secret Code: " + secretCode + "<br>" +
                        "Sponsor ID: " + sponsorID;
		sendEmailAsync(user.getEmail(), "Welcome to Super Coin",content);
		System.err.println(user);
		session.setAttribute("userEmail", user.getEmail());
		System.err.println(
				"User ID: " + user.getId() + " || Password Code: " + user.getPassword() + " || Email: "
						+ user.getEmail());
		session.setAttribute("verification", true);
		return "redirect:/login?success";
	}
	@Async
    public void sendEmailAsync(String to, String subject, String body) {
        emailService.sendEmail(to, subject, body);
    }
}
