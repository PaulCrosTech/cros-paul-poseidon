package com.nnk.springboot.domain;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

/**
 * The Trade entity
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "trade")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trade_id")
    private Integer id;

    @Column(nullable = false, length = 30)
    private String account;

    @Column(nullable = false, length = 30)
    private String type;

    private Double buyQuantity;

    private Double sellQuantity;

    private Double buyPrice;

    private Double sellPrice;

    @Column(columnDefinition = "TIMESTAMP")
    private Instant tradeDate;

    @Column(length = 125)
    private String security;

    @Column(length = 10)
    private String status;

    @Column(length = 125)
    private String trader;

    @Column(length = 125)
    private String benchmark;

    @Column(length = 125)
    private String book;

    @Column(length = 125)
    private String creationName;

    @Column(columnDefinition = "TIMESTAMP")
    @CreationTimestamp
    private Instant creationDate;

    @Column(length = 125)
    private String revisionName;

    @Column(columnDefinition = "TIMESTAMP")
    private Instant revisionDate;

    @Column(length = 125)
    private String dealName;

    @Column(length = 125)
    private String dealType;

    @Column(length = 125)
    private String sourceListId;

    @Column(length = 125)
    private String side;

}
