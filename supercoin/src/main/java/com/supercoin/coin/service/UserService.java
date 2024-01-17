package com.supercoin.coin.service;

import org.springframework.stereotype.Service;

import com.supercoin.coin.model.User;

@Service
public interface UserService {

	Integer saveUserId(User user);

	String getUserName(Integer Id);
	
	Boolean checkUser(Integer Id);

}
