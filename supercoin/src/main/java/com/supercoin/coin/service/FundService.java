package com.supercoin.coin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supercoin.coin.model.Fund;
import com.supercoin.coin.repository.FundRepository;

@Service
public class FundService {
	 @Autowired
	    private FundRepository fundRepository;

	    public List<Fund> getFundsByStatus(boolean status, Integer id) {
	        return fundRepository.findByStatusAndUserId(status,id);
	    }
	    
	    public double calculateTotalAmount(Integer id) {
	        List<Fund> funds = fundRepository.findByStatusAndUserId(true,id); 
	        double totalAmount = 0;
	        for (Fund fund : funds) {
	            totalAmount += fund.getAmmount();
	        }
	        return totalAmount;
	    }
}
