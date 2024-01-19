package com.supercoin.coin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.supercoin.coin.model.User;
import com.supercoin.coin.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class ProfileController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/profile")
	public String profile(HttpSession session, Model model) {

		Integer userId = (Integer) session.getAttribute("userid");
		Boolean auth = (Boolean) session.getAttribute("authentication");

		if (userId != null && Boolean.TRUE.equals(auth)) {
			User user = userRepository.findById(userId).orElse(null);
			if (user != null) {
				model.addAttribute("user", user);
				
				return "profile";
			}
		}

		session.setAttribute("access", false);
		return "redirect:login?accessDenied";

	}

	@PostMapping("changePassword")
	public String changePassword(HttpServletRequest request, HttpSession session) {
		Integer userId = (Integer) session.getAttribute("userid");
		Boolean auth = (Boolean) session.getAttribute("authentication");

		if (userId != null && Boolean.TRUE.equals(auth)) {
			User user = userRepository.findById(userId).orElse(null);
			if (user != null) {

				if (request.getParameter("oldPassword").equals(user.getPassword())
						&& request.getParameter("newPassword").equals(request.getParameter("newPasswordCopy"))) {
					user.setPassword(request.getParameter("newPassword"));

					userRepository.save(user);
					session.setAttribute("feedMsg", "Success!! Password has been chnaged. ");
					return "redirect:profile?passwordChanged";
				}
				session.setAttribute("noChange", true);
				return "redirect:profile?noChange";
			}
		}

		session.setAttribute("access", false);
		return "redirect:login?accessDenied";

	}

	@PostMapping("changesecuritycode")
	public String changeSecurityCode(HttpServletRequest request, HttpSession session) {
		Integer userId = (Integer) session.getAttribute("userid");
		Boolean auth = (Boolean) session.getAttribute("authentication");

		if (userId != null && Boolean.TRUE.equals(auth)) {
			User user = userRepository.findById(userId).orElse(null);
			if (user != null) {

				if (request.getParameter("oldSecCode").equals(user.getCode())
						&& request.getParameter("newSecCode").equals(request.getParameter("newSecCodeCopy"))) {
					user.setCode(request.getParameter("newSecCode"));
					userRepository.save(user);
					session.setAttribute("feedMsg", "Success!! Security code has been chnaged. ");
					return "redirect:profile?securityCodeChanged";
				}
				session.setAttribute("noChange", true);
				return "redirect:profile?noChange";
			}
		}

		session.setAttribute("access", false);
		return "redirect:login?accessDenied";

	}

}
