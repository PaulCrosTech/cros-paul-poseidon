package com.nnk.springboot.dto;

import com.nnk.springboot.validators.annotations.ValidNotNullAndPositive;
import lombok.Data;

/**
 * The CurveDto class
 */
@Data
public class CurvePointDto {

    private Integer id;

    @ValidNotNullAndPositive
    private Double term;

    @ValidNotNullAndPositive
    private Double value;

}
