package com.nnk.springboot.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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

    @NotNull(message = "Order Number is mandatory")
    @Positive(message = "Order Number should be a positive number")
    private Integer orderNumber;

}
