package com.supercoin.coin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.supercoin.coin.model.Transaction;
import com.supercoin.coin.model.User;
import com.supercoin.coin.repository.TransactionRepository;
import com.supercoin.coin.repository.UserRepository;
import com.supercoin.coin.service.EmailService;

import jakarta.servlet.http.HttpSession;

@Controller
public class TransactionController {
	@Autowired
	private EmailService emailService;
	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private UserRepository userRepository;
	@PostMapping("/save-utr")
	public String saveUtr(@RequestParam("upiRefNo") String upiRefNo, @RequestParam("txAmmount") Float ammount,
			@RequestParam("method") String method, HttpSession session,RedirectAttributes redirectAttributes) {
		session.setAttribute("SubmitAuth", true);
		try {
			// Check if the UTR already exists in the database
			Transaction existingTransaction = transactionRepository.findByRefId(upiRefNo);
			if (existingTransaction != null) {
				// UTR already exists, handle accordingly (e.g., show error message)
				// UTR was not found, redirect to error page
				String userEmail = (String) session.getAttribute("userEmail");
				emailService.sendEmail(userEmail, "Payment Rejection - Oxyclouds",
						"Your payment has already been processed.");
				System.out.println("Already Redeemed");
				session.setAttribute("SubmitAuthError", true);
				redirectAttributes.addFlashAttribute("error", true);
				// Redirect to an error page or return a response indicating UTR already exists
				return "redirect:/depositwallet";
			}

			// Create a new User object
			Transaction newTransaction = new Transaction();
			session.setAttribute("newTransaction", newTransaction);
			newTransaction.setRefId(upiRefNo);
			newTransaction.setStatus(false);
			newTransaction.setAmmount((int) ammount.floatValue());
			Integer userId = (Integer) session.getAttribute("userid");
			User user = userRepository.findById(userId).orElse(null);
			newTransaction.setUserId(userId);
			newTransaction.setMethod(method);
			// Save the new User object to the database
			transactionRepository.save(newTransaction);
			session.setAttribute("submittedUtr", newTransaction.getRefId());
			session.setAttribute("moneySent", newTransaction.getAmmount());
			session.setAttribute("userEmail", user.getEmail());
			// Redirect to the fetchEmail page to check for valid UTR
			return "redirect:/fetchEmail";
		} catch (DataIntegrityViolationException e) {
			session.setAttribute("SubmitAuthError", true);
			// Handle data integrity violation (e.g., unique constraint violation)
			return "redirect:/depositwallet";
		} catch (DataAccessException e) {
			// Handle generic data access exception
			session.setAttribute("SubmitAuthError", true);
			return "redirect:/depositwallet";
		} catch (Exception e) {
			// Handle any other unexpected exceptions
			session.setAttribute("SubmitAuthError", true);
			return "redirect:/depositwallet";
		}
	}
}
