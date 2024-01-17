package com.supercoin.coin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supercoin.coin.model.User;
import com.supercoin.coin.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public Integer saveUserId(User user) {
		userRepository.save(user);
		return user.getId();
	}

	@Override
	public String getUserName(Integer Id) {

		if (checkUser(Id)) {
			return userRepository.getById(Id).getName();
		}
		return "No Such User";
	}

	@Override
	public Boolean checkUser(Integer Id) {
		return userRepository.existsById(Id);
	}

}
