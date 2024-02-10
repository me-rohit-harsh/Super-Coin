package com.supercoin.coin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.supercoin.coin.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	@SuppressWarnings("unchecked")
	User save(User user);

	User findByIdAndPassword(Integer id, String password);

	List<User> findAllBySponserId(Integer id);

	@Query(value = "WITH RECURSIVE ReferralHierarchy AS (" +
			"    SELECT * FROM user WHERE User_ID = ?1" +
			"    UNION ALL" +
			"    SELECT u.* FROM user u JOIN ReferralHierarchy r ON u.Sponser_ID = r.User_ID" +
			")" +
			"SELECT * FROM ReferralHierarchy", nativeQuery = true)
	List<User> findUsersBelow(Integer userId);

}
