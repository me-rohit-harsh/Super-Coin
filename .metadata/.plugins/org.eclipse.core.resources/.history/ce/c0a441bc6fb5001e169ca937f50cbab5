package com.supercoin.coin.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
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
	private String docNumber;

	@Column(name = "Document_type")
	private String docType;

	@Lob
	@Column(name = "Front_image", columnDefinition = "LONGBLOB")
	private byte[] frontImage;

	@Lob
	@Column(name = "Back_image", columnDefinition = "LONGBLOB")
	private byte[] backImage;

	@Column(name = "Verification_status")
	private String status;

}
