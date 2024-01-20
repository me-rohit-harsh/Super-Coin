package com.supercoin.coin.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Query_ticket")
@Data
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Ticket_id")
    private Integer id;

    @Column(name = "QueryUser_id")
    private Integer userId;

    @Column(name = "topic", length = 255, columnDefinition = "TINYTEXT")
    private String topic;

    @Column(name = "message", length = 65535, columnDefinition = "LONGTEXT")
    private String message;

    @Column(name = "reply", length = 65535, columnDefinition = "LONGTEXT")
    private String reply;

    @Column(name = "time_stamp")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date timeStamp = new Date();

    @Column(name = "ticket_status")
    private String status;

    public Ticket() {

    }
}
