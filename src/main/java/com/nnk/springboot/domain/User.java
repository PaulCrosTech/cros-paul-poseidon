package com.nnk.springboot.domain;


import com.nnk.springboot.validators.annotations.ValidFullname;
import com.nnk.springboot.validators.annotations.ValidPassword;
import com.nnk.springboot.validators.annotations.ValidRole;
import com.nnk.springboot.validators.annotations.ValidUsername;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @ValidUsername
    @Column(length = 125)
    private String username;

    @ValidPassword
    @Column(length = 125)
    private String password;

    @ValidFullname
    @Column(length = 125)
    private String fullname;

    @ValidRole
    @Column(length = 125)
    private String role;
}
