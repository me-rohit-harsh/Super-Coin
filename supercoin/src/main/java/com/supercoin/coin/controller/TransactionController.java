package com.supercoin.coin.controller;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.supercoin.coin.service.TransactionService;

import jakarta.servlet.http.HttpSession;

import com.supercoin.coin.model.Transaction;
import com.supercoin.coin.model.User;
import com.supercoin.coin.repository.UserRepository;

import java.util.List;

@Controller
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    
    @PostMapping("/addMoney")
    public void addMoney(@RequestParam BigDecimal initialAmount, @RequestParam LocalDate startDate) {
        transactionService.addMoney(initialAmount, startDate);
    }
    
	@Autowired
	private UserRepository userRepository;

	@GetMapping("/income")
	public String income(HttpSession session, Model model) {
		
		Integer userId = (Integer) session.getAttribute("userid");
	    Boolean auth = (Boolean) session.getAttribute("authentication");

	    if (userId != null && Boolean.TRUE.equals(auth)) {
	        User user = userRepository.findById(userId).orElse(null);
	        if (user != null) {
	            model.addAttribute("user", user);
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++");

                for(int i=0;i<transactionService.getTransactionHistory().size();i++){
                    System.out.println(i);
                }
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++");
                model.addAttribute("histroy", transactionService.getTransactionHistory());
	            return "income";
	        }
	    }

	    session.setAttribute("access", false);
	    return "redirect:login?accessDenied";
		
	}
    // @GetMapping("/history")
    // public List<Transaction> getTransactionHistory() {
    //     return transactionService.getTransactionHistory();
    // }
}
