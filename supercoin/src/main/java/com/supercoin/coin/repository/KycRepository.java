package com.supercoin.coin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.supercoin.coin.model.KYC;


public interface KycRepository extends JpaRepository<KYC, Integer> {


	
}
