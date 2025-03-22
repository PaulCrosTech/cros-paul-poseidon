package com.nnk.springboot.dto;

import com.nnk.springboot.validators.annotations.ValidNotNullAndPositive;
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

    @ValidNotNullAndPositive
    private Integer orderNumber;

}
