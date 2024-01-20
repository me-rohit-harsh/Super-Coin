package com.supercoin.coin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.supercoin.coin.model.Team;


@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {
	
    List<Team> findByRefId(Integer refId);


}
