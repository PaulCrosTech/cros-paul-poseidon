package com.nnk.springboot.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * The Rating DTO
 */
@Data
public class RatingDto {

    private Integer id;

    @Length(min = 1, max = 125, message = "Moodys Rating should be between 1 and 125 characters")
    private String moodysRating;

    @Length(min = 1, max = 125, message = "Sand P Rating should be between 1 and 125 characters")
    private String sandPRating;

    @Length(min = 1, max = 125, message = "Fitch Rating should be between 1 and 125 characters")
    private String fitchRating;

    @Min(value = -127, message = "Order Number should be between -127 and 127")
    @Max(value = 127, message = "Order Number should be between -127 and 127")
    private Integer orderNumber;

}
