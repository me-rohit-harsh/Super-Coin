package com.supercoin.coin.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "team_table")
public class Team {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "team_id")
	private Integer id;

	@Column(name = "user_id")
	private Integer userId;

	@Column(name = "user_name")
	private String name;

	@Column(name = "user_DOJ")
	private Date DOJ = new Date();

	@Column(name = "user_inv_status")
	private String status;

	@Column(name = "user_mob")
	private String mobNo;

	@Column(name = "ref_id")
	private Integer refId;

	@OneToMany(mappedBy = "team")
	private List<User> users;
}
