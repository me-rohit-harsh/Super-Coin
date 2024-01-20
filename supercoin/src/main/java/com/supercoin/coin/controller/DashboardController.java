package com.supercoin.coin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.supercoin.coin.model.User;
import com.supercoin.coin.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
	
	
	@Autowired
	private  UserRepository userRepository;
	@GetMapping
	public String dashboard(HttpSession session, Model model) {
	    // Check if session authentication is true and it has a valid user, then let them access their account dashboard
	    Integer userId = (Integer) session.getAttribute("userid");
	    Boolean auth = (Boolean) session.getAttribute("authentication");

	    if (userId != null && Boolean.TRUE.equals(auth)) {
	        User user = userRepository.findById(userId).orElse(null);
	        if (user != null) {
	            model.addAttribute("user", user);
	            return "dashboard";
	        }
	    }

	    session.setAttribute("access", false);
	    return "redirect:login?accessDenied";
	}



}
