package com.supercoin.coin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.supercoin.coin.model.Fund;


public interface FundRepository extends JpaRepository<Fund, Integer> {

	Fund findByRefId(String upiRefNo);

	List<Fund> findByUserId(Integer id);

	List<Fund> findByStatusAndUserId(boolean status, Integer id);
	
}
