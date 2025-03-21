package com.nnk.springboot.dto;


import com.nnk.springboot.validators.annotations.ValidFullname;
import com.nnk.springboot.validators.annotations.ValidPassword;
import com.nnk.springboot.validators.annotations.ValidRole;
import com.nnk.springboot.validators.annotations.ValidUsername;
import lombok.Data;

/**
 * DTO for user
 */
@Data
public class UserDto {

    private Integer id;

    @ValidUsername
    private String username;

    @ValidPassword
    private String password;

    @ValidFullname
    private String fullname;

    @ValidRole
    private String role;


}
