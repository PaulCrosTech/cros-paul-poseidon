package com.nnk.springboot.dto;

import com.nnk.springboot.validators.annotations.ValidNotNullAndPositive;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * DTO for bid
 */
@Getter
@Setter
public class BidDto extends AbstractDto {

    @NotEmpty
    @Length(min = 1, max = 30)
    private String account;

    @NotEmpty
    @Length(min = 1, max = 30)
    private String type;

    @ValidNotNullAndPositive
    private Double bidQuantity;


}
