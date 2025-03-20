package com.nnk.springboot.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "rule_name")
public class RuleName {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ruleNameId;

    @Column(length = 125)
    private String name;

    @Column(length = 125)
    private String description;

    @Column(length = 125)
    private String json;

    @Column(length = 512)
    private String template;

    @Column(length = 125)
    private String sqlStr;

    @Column(length = 125)
    private String sqlPart;

}
