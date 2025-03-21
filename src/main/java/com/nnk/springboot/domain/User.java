package com.nnk.springboot.domain;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The User entity
 */
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
    @Column(name = "user_id")
    private Integer id;

    @Column(length = 125)
    private String username;

    @Column(length = 125)
    private String password;

    @Column(length = 125)
    private String fullname;

    @Column(length = 125)
    private String role;
}
