package com.supercoin.coin.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.supercoin.coin.model.Ticket;
import com.supercoin.coin.model.User;
import com.supercoin.coin.repository.TicketRepository;
import com.supercoin.coin.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SuportController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TicketRepository ticketRepository;

	@GetMapping("/support")
	public String directTeam(HttpSession session, Model model) {

		Integer userId = (Integer) session.getAttribute("userid");
		Boolean auth = (Boolean) session.getAttribute("authentication");

		if (userId != null && Boolean.TRUE.equals(auth)) {
			User user = userRepository.findById(userId).orElse(null);
			if (user != null) {
				model.addAttribute("user", user);
				
				List<Ticket> ticktes = ticketRepository.findByUserId(userId);
				List<Ticket> ticketList = new ArrayList<Ticket>();
				for (int i = ticktes.size() - 1; i >= 0; i--) {
					Ticket ticket = ticktes.get(i);
					ticketList.add(ticket);
				}
				model.addAttribute("ticketList", ticketList);
				return "support";
			}
		}

		session.setAttribute("access", false);
		return "redirect:login?accessDenied";

	}

	@PostMapping("/newTicket")
	public String newTicket(@ModelAttribute("ticket") Ticket ticket, HttpSession session, Model model) {
		Integer userId = (Integer) session.getAttribute("userid");
		Boolean auth = (Boolean) session.getAttribute("authentication");

		if (userId != null && Boolean.TRUE.equals(auth)) {
			User user = userRepository.findById(userId).orElse(null);
			if (user != null) {
				ticket.setStatus("Open");
				ticket.setUserId(userId);
				ticketRepository.save(ticket);
				user.setTicket(ticket);
				userRepository.save(user);
				model.addAttribute("user", user);
				session.setAttribute("newTicketMsg", "Your Ticket has been raised!");
				return "redirect:support?raised";
			}
			session.setAttribute("newTicketErrMsg", "Oops.. Somethingh gone wrong. ");
			return "redirect:support?notRaised";
		}

		session.setAttribute("access", false);
		return "redirect:login?accessDenied";

	}

}
