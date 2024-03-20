package com.supercoin.coin.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.supercoin.coin.model.User;
import com.supercoin.coin.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class WithdrawController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/withdrwal")
    public String withdrwal(HttpSession session, Model model) {

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

    @PostMapping("/findBeneficiaryById")
    public ResponseEntity<?> findBeneficiaryById(@RequestBody String userId, HttpSession session) {
        Integer parsedUserId = Integer.parseInt(userId); // Parsing userId from string to integer
        Integer sessionUserId = (Integer) session.getAttribute("userid");

        if (sessionUserId != null && !parsedUserId.equals(sessionUserId)) {
            User user = userRepository.findById(parsedUserId).orElse(null);
            if (user != null) {
                return ResponseEntity.ok().body(new UserResponse(true, user));
            }
        }
        return ResponseEntity.ok().body(new UserResponse(false, null));
    }

    @PostMapping("maketransfer")
    public String makeTransfer(@RequestParam("amount") Integer amount, @RequestParam("securityCode") String code,
            @RequestParam("userId") Integer beneficiaryID, HttpSession session,
            RedirectAttributes redirectAttributes) {
        Integer userId = (Integer) session.getAttribute("userid");
        if (userId == null) {
            // User not logged in, redirect to login page or handle appropriately
            session.setAttribute("access", false);
            return "redirect:login?accessDenied";
        }

        User user = userRepository.findById(userId).orElse(null);
        User beneficiary = userRepository.findById(beneficiaryID).orElse(null);

        if (user == null || beneficiary == null) {
            // Either user or beneficiary not found, handle appropriately
            redirectAttributes.addFlashAttribute("errorMessage", "User or beneficiary not found!");
            return "redirect:/transfer";
        }

        // Validate security code
        if (!code.equals(user.getCode())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Incorrect Security Code!");
            return "redirect:/transfer";
        }

        // Check if user has sufficient balance
        if (user.getDeposite() < amount) {
            redirectAttributes.addFlashAttribute("errorMessage", "Insufficient balance for withdrawal!");
            return "redirect:/transfer";
        }

        // Perform the transfer
        try {
            user.setDeposite(user.getDeposite() - amount);
            beneficiary.setDeposite(beneficiary.getDeposite() + amount);

            userRepository.save(user);
            userRepository.save(beneficiary);

            redirectAttributes.addFlashAttribute("successMessage", "Funds transferred successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Oops! Something went wrong.");
        }

        return "redirect:/transfer";
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

    @PostMapping("/withdraw")
    public String withdrawRequest(@RequestParam("amount") Integer amount, @RequestParam("securityCode") String code,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        Integer userId = (Integer) session.getAttribute("userid");
        User user = userRepository.findById(userId).orElse(null);
        if (code.equals(user.getCode())) {
            Integer oldAmount = user.getDeposite();
            if (oldAmount >= amount) {
                user.setDeposite(oldAmount - amount);
                userRepository.save(user); // Save the updated user object
                redirectAttributes.addFlashAttribute("successMessage", "Withdrawal successful!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Insufficient balance for withdrawal!");
            }
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Incorrect Security Code!");
        }
        return "redirect:/withdrwal";
    }

}
