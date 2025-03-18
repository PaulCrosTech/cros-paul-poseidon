package com.nnk.springboot.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer userId;

    @NotBlank(message = "Username is mandatory")
    @Column(length = 125)
    private String username;

    @NotBlank(message = "Password is mandatory")
    @Column(length = 125)
    private String password;

    @NotBlank(message = "FullName is mandatory")
    @Column(length = 125)
    private String fullname;

    @NotBlank(message = "Role is mandatory")
    @Column(length = 125)
    private String role;
}
