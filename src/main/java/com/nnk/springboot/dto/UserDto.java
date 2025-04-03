package com.nnk.springboot.dto;


import com.nnk.springboot.validators.annotations.ValidFullname;
import com.nnk.springboot.validators.annotations.ValidPassword;
import com.nnk.springboot.validators.annotations.ValidRole;
import com.nnk.springboot.validators.annotations.ValidUsername;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO for user
 */
@Getter
@Setter
@NoArgsConstructor
public class UserDto extends AbstractDto {

    @ValidUsername
    private String username;

    @ValidPassword
    private String password;

    @ValidFullname
    private String fullname;

    @ValidRole
    private String role;


}
