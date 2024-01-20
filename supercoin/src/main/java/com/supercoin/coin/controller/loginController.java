package com.supercoin.coin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.supercoin.coin.model.User;
import com.supercoin.coin.repository.UserRepository;
import com.supercoin.coin.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class loginController {

	@Autowired
	private UserRepository userRepository;

	public loginController(UserService userService) {
		super();
	}

	public loginController() {
		super();
	}

	@GetMapping
	public String login(HttpSession session, Model model) {
		// Check if session authentication is true and it has a valid user, then let
		// them access their account dashboard
		Integer userId = (Integer) session.getAttribute("userid");
		Boolean auth = (Boolean) session.getAttribute("authentication");

		if (userId != null && Boolean.TRUE.equals(auth)) {
			User user = userRepository.findById(userId).orElse(null);
			if (user != null) {
				model.addAttribute("user", user);
				return "redirect:dashboard";
			}
		}
		return "login";
	}

	@PostMapping("authenticate")
	public String login(HttpServletRequest request, HttpSession session) {
		try {
			Integer id = Integer.parseInt(request.getParameter("id"));
			String password = request.getParameter("password");

			User loggedInUser = userRepository.findByIdAndPassword(id, password);
			if (loggedInUser != null) {
				System.out.println("user logged in");
				session.setAttribute("userid", loggedInUser.getId());
				session.setAttribute("authentication", true);
				return "redirect:/dashboard";
			}
		} catch (Exception e) {
			session.setAttribute("authentication", false);
			return "redirect:/login?error=InvalidUserId";
		}

		session.setAttribute("authentication", false);
		return "redirect:/login?error";
	}

}
