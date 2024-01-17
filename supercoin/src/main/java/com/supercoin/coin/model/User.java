package com.supercoin.coin.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Table(name = "user")
@Data
public class User {
	@Id
	@Column(name = "User_ID")
	@GeneratedValue
	private Integer id;

	@Column(name = "Sponser_ID")
	private Integer sponserId;
	
	@Column(name = "Name")
	private String name;
	
	@Column(name = "Email")
	private String email;
	
	@Column(name = "Mobile No.")
	private String mob;
	
	@Column(name = "Secrete_code")
	private String code;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "Gender")
	private String gender;
	
	@Column(name = "Nominee Name")
	private String nominee;
	
	@Column(name = "Earned Coin")
	private int coin;
	
	@Column(name = "Deposite Coin")
	private int deposite;
	
	@Column(name = "Reg_Date")
	@Temporal(TemporalType.DATE)
	final private Date regDate = new Date();
	
	@Column(name = "Inv_Date")
	@Temporal(TemporalType.DATE)
	private Date invDate;
	
	@OneToOne
	private KYC kyc;
	
}
