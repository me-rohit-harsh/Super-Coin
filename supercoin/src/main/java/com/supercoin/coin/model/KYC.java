package com.supercoin.coin.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity(name = "KYC_table")
@Table
public class KYC {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "Name")
	 private String name;

	@Column(name = "Age")
	private Integer age;

	@Column(name = "Document_number")
	private Long docNumber;

	@Column(name = "Document_type")
	private String docType;

	@Column(name = "Front_image")
	private byte[] front;

	@Column(name = "Back_image")
	private byte[] back;

	@Column(name = "Verification_status")
	private String status;

	
}
