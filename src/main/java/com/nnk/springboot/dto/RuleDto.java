package com.nnk.springboot.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * The Rule Dto
 */
@Getter
@Setter
@NoArgsConstructor
public class RuleDto extends AbstractDto {


    @Length(min = 1, max = 125, message = "Name should be between 1 and 125 characters")
    private String name;

    @Length(min = 1, max = 125, message = "Description should be between 1 and 125 characters")
    private String description;

    @Length(min = 1, max = 125, message = "Json should be between 1 and 125 characters")
    private String json;

    @Length(min = 1, max = 512, message = "Template should be between 1 and 512 characters")
    private String template;

    @Length(min = 1, max = 125, message = "SqlStr should be between 1 and 125 characters")
    private String sqlStr;

    @Length(min = 1, max = 125, message = "SqlPart should be between 1 and 125 characters")
    private String sqlPart;
}
