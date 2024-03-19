package com.supercoin.coin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.supercoin.coin.model.Fund;
import com.supercoin.coin.repository.FundRepository;
import com.supercoin.coin.repository.UserRepository;
import com.supercoin.coin.service.EmailService;
import com.supercoin.coin.model.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class EmailController {

	@Autowired
	private EmailService emailService;
	@Autowired
	private FundRepository fundRepository;
	@Autowired
	private UserRepository userRepository;

	@GetMapping("/fetchEmail")
	public String fetchEmails(HttpSession session, RedirectAttributes redirectAttributes) {

		try {

			// Retrieve submittedUtr and moneySent from session attributes
			String submittedUtr = (String) session.getAttribute("submittedUtr");
			Integer moneySent = (Integer) session.getAttribute("moneySent");

			// Call fetchEmails from emailService and get the result
			boolean utrFound = emailService.fetchEmails(submittedUtr, moneySent);

			// Set the status based on the result
			if (utrFound) {
				// UTR was found, set status accordingly
				Fund newFund = (Fund) session.getAttribute("newFund");
				newFund.setStatus(true);
				newFund.setType("Credited");
				
				fundRepository.save(newFund);
				// Updating the deposit amount / Fund balance  
				Integer userId = (Integer) session.getAttribute("userid");
				User user = userRepository.findById(userId).orElse(null);
				Integer oldCoin=user.getCoin();
				Integer oldMoney = user.getDeposite();
				user.setDeposite(oldMoney + moneySent);
				oldMoney=user.getDeposite();
				user.setCoin(oldMoney*4);
				userRepository.save(user);
				
				// Call the sendEmail method asynchronously
				String userEmail = (String) session.getAttribute("userEmail");
				sendEmailAsync(userEmail, "Payment Confirmation - Welcome to Super Coin",
						"Welcome to the Super Coin. Your payment of Rs" + moneySent
								+ " has been successfully received.");
				redirectAttributes.addFlashAttribute("success", true);
				return "redirect:depositwallet";
			} else {
				// UTR was not found, redirect to error page
				String userEmail = (String) session.getAttribute("userEmail");
				emailService.sendEmail(userEmail, "Payment Rejection - Super Coin",
						"Welcome to the Super Coin. Your payment of Rs" + moneySent
								+ " has failed  .");
				session.setAttribute("SubmitAuthError", true);
				redirectAttributes.addFlashAttribute("error", true);
				return "redirect:depositwallet";
			}
		} catch (Exception e) {
			// Handle exceptions
			// e.printStackTrace(); // Print the stack trace for debugging
			System.out.println("Exception occurs in the email Controller" + e);
			redirectAttributes.addFlashAttribute("error", true);
			return "redirect:depositwallet";
		}
	}

	@Async
	public void sendEmailAsync(String to, String subject, String body) {
		emailService.sendEmail(to, subject, body);
	}
}
