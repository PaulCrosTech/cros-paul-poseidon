package com.nnk.springboot.dto;

import com.nnk.springboot.validators.annotations.ValidNotNullAndPositive;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * The CurveDto class
 */
@Data
public class CurvePointDto {

    private Integer id;

    @NotNull(message = "CurvePoint Id number is mandatory, between -127 and 127")
    @Min(value = -127, message = "CurvePoint Id number should be between -127 and 127")
    @Max(value = 127, message = "CurvePoint Id number should be between -127 and 127")
    private Integer curveId;

    @ValidNotNullAndPositive
    private Double term;

    @ValidNotNullAndPositive
    private Double value;

}
