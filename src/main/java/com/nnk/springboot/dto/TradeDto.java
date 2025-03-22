package com.nnk.springboot.dto;


import com.nnk.springboot.validators.annotations.ValidNotNullAndPositive;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * The Trade DTO
 */
@Data
public class TradeDto {

    private Integer id;

    @Length(min = 1, max = 30, message = "Account should be between 1 and 30 characters")
    private String account;

    @Length(min = 1, max = 30, message = "Type should be between 1 and 30 characters")
    private String type;

    @ValidNotNullAndPositive
    private Double buyQuantity;
    
}
