package com.nnk.springboot.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;


@Entity
@Data
@NoArgsConstructor
@Table(name = "curve_point")
public class CurvePoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int curvePointId;

    @Column(columnDefinition = "TINYINT")
    private int curveId;

    @Column(columnDefinition = "TIMESTAMP")
    private Instant asOfDate;

    private Double term;

    private Double value;

    @Column(columnDefinition = "TIMESTAMP")
    private Instant creationDate;

}
