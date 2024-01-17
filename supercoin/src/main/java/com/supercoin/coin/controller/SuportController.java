package com.supercoin.coin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.supercoin.coin.model.User;
import com.supercoin.coin.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class SuportController {
	
	@Autowired
	private UserRepository userRepository;

	@GetMapping("/support")
	public String directTeam(HttpSession session, Model model) {
		
		Integer userId = (Integer) session.getAttribute("userid");
	    Boolean auth = (Boolean) session.getAttribute("authentication");

	    if (userId != null && Boolean.TRUE.equals(auth)) {
	        User user = userRepository.findById(userId).orElse(null);
	        if (user != null) {
	            model.addAttribute("user", user);
	            return "support";
	        }
	    }

	    session.setAttribute("access", false);
	    return "redirect:login?accessDenied";
		
	}

}
