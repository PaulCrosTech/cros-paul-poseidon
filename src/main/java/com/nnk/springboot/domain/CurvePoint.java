package com.nnk.springboot.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

/**
 * The CurvePoint entity
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "curve_point")
public class CurvePoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "curve_point_id")
    private Integer id;

    @Column(columnDefinition = "TINYINT")
    private Integer curveId;

    @Column(columnDefinition = "TIMESTAMP")
    private Instant asOfDate;

    private Double term;

    private Double value;

    @Column(columnDefinition = "TIMESTAMP")
    @CreationTimestamp
    private Instant creationDate;

}
