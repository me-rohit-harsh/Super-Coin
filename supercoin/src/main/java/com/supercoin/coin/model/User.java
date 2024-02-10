package com.supercoin.coin.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "user")
@Data
public class User {
	@Id
	@Column(name = "User_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "Sponser_ID")
	private Integer sponserId;

	@ManyToOne
	@JoinColumn(name = "team_id")
	private Team team;

	@Column(name = "Name")
	private String name;

	@Column(name = "Email")
	private String email;

	@Column(name = "Mobile_No")
	private String mob;

	@Column(name = "Secrete_code")
	private String code;

	@Column(name = "password")
	private String password;

	@Column(name = "Gender")
	private String gender;

	@Column(name = "Nominee_Name")
	private String nominee;

	@Column(name = "Earned_Coin")
	private int coin;

	@Column(name = "Deposite_Coin")
	private int deposite;

	@Column(name = "Reg_Date")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	final private Date regDate = new Date();

	@Column(name = "Inv_Date")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date invDate = new Date();

	@OneToOne(optional = true)
	@JoinColumn(name = "kyc_id")
	private KYC kyc;

	@OneToOne(optional = true)
	@JoinColumn(name = "ticket_id")
	private Ticket ticket;
}
