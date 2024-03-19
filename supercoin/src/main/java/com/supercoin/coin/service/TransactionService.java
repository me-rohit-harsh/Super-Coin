package com.supercoin.coin.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supercoin.coin.repository.TransactionRepository;
import com.supercoin.coin.model.Transaction;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    
    public void addMoney(BigDecimal initialAmount, LocalDate startDate) {
        BigDecimal currentAmount = initialAmount;
        LocalDate currentDate = startDate;
        
        while (currentDate.isBefore(LocalDate.now())) {
            currentAmount = currentAmount.add(currentAmount.multiply(BigDecimal.valueOf(0.005))); // Daily increase of 0.5%
            Transaction transaction = new Transaction();
         
            transaction.setCrAmount(currentAmount);
            transactionRepository.save(transaction);
            currentDate = currentDate.plusDays(1);
        }
    }
    
    public List<Transaction> getTransactionHistory() {
        return transactionRepository.findAll();
    }
}
