package com.supercoin.coin.model;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Table(name="fund")
@Data
public class Fund {
	 	@Id
	    @Column(name = "Fund_id")
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer id;
	 	
	 	@Column(name="User_id")
	    private Integer userId;

	    @Column(name = "Tx_ammount")
	    private Integer ammount;

	    @Column(name = "Tx_status")
	    private Boolean status;

	    @Column(name = "Ref_id", unique = true)
	    private String refId;

	    @Column(name = "Fund_type")
	    private String type;
	    
	    @Column(name = "Method")
	    private String method;

	    // Automatically set to the current timestamp when the entity is created
	    @CreationTimestamp
	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "created_at", updatable = false)
	    private Date createdAt;

	    // Automatically updated to the current timestamp when the entity is updated
	    @UpdateTimestamp
	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "updated_at", nullable = false)
	    private Date updatedAt;
}
