package com.supercoin.coin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.supercoin.coin.model.Transaction;


public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

	Transaction findByRefId(String upiRefNo);

	List<Transaction> findByUserId(Integer id);

	List<Transaction> findByStatusAndUserId(boolean status, Integer id);
	
}
