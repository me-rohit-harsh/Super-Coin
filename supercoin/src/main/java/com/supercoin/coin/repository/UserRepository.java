package com.supercoin.coin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.supercoin.coin.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	@SuppressWarnings("unchecked")
	User save(User user);

	User findByIdAndPassword(Integer id, String password);
	
	
	
	
}
