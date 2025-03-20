package com.nnk.springboot.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(
        name = "user",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"username"}, name = "username_UNIQUE"),
        }
)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(length = 125)
    private String username;

    @Column(length = 125)
    private String password;

    @Column(length = 125)
    private String fullname;

    @Column(length = 125)
    private String role;
}
