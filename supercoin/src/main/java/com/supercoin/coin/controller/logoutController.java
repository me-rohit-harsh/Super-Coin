package com.supercoin.coin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/logout")
public class logoutController {

	@GetMapping
	public String logout(HttpSession session, SessionStatus sessionStatus) {
        // Invalidate the user's session
        session.invalidate();
        sessionStatus.setComplete(); // Mark the session as complete
        System.out.println("Session invalidated successfully for user ");
        return "redirect:/login";
    }
}
