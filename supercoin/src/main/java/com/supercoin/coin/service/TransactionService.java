package com.supercoin.coin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supercoin.coin.model.Transaction;
import com.supercoin.coin.repository.TransactionRepository;

@Service
public class TransactionService {
	 @Autowired
	    private TransactionRepository transactionRepository;

	    public List<Transaction> getTransactionsByStatus(boolean status, Integer id) {
	        return transactionRepository.findByStatusAndUserId(status,id);
	    }
	    
	    public double calculateTotalAmount(Integer id) {
	        List<Transaction> transactions = transactionRepository.findByStatusAndUserId(true,id); 
	        double totalAmount = 0;
	        for (Transaction transaction : transactions) {
	            totalAmount += transaction.getAmmount();
	        }
	        return totalAmount;
	    }
}
