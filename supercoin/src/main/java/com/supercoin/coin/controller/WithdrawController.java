package com.supercoin.coin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.supercoin.coin.model.User;
import com.supercoin.coin.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class WithdrawController {

    @Autowired
	private UserRepository userRepository;

    @GetMapping("/withdrwal")
    public String withdrwal(HttpSession session,Model model) {
    
		Integer userId = (Integer) session.getAttribute("userid");
		Boolean auth = (Boolean) session.getAttribute("authentication");

		if (userId != null && Boolean.TRUE.equals(auth)) {
			User user = userRepository.findById(userId).orElse(null);
			if (user != null) {
				model.addAttribute("user", user);

				model.addAttribute("directMember", userRepository.findAllBySponserId(userId));

				return "withdrwal";
			}
		}
		
        session.setAttribute("access", false);
        return "redirect:login?accessDenied";

	}

    @GetMapping("/withdrwalstatus")
    public String status(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userid");
        Boolean auth = (Boolean) session.getAttribute("authentication");

        if (userId != null && Boolean.TRUE.equals(auth)) {
            User user = userRepository.findById(userId).orElse(null);
            if (user != null) {
                model.addAttribute("user", user);
                return "withdrwalstatus";
            }
        }

        session.setAttribute("access", false);
        return "redirect:login?accessDenied";
    }

    @GetMapping("/transfer")
    public String transfer(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userid");
        Boolean auth = (Boolean) session.getAttribute("authentication");

        if (userId != null && Boolean.TRUE.equals(auth)) {
            User user = userRepository.findById(userId).orElse(null);
            if (user != null) {
                model.addAttribute("user", user);
                return "transfer";
            }
        }

        session.setAttribute("access", false);
        return "redirect:login?accessDenied";
    }
    @GetMapping("/statement")
    public String statement(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userid");
        Boolean auth = (Boolean) session.getAttribute("authentication");

        if (userId != null && Boolean.TRUE.equals(auth)) {
            User user = userRepository.findById(userId).orElse(null);
            if (user != null) {
                model.addAttribute("user", user);
                return "statement";
            }
        }

        session.setAttribute("access", false);
        return "redirect:login?accessDenied";
    }


}
