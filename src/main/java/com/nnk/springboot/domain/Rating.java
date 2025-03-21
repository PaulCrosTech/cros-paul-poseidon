package com.nnk.springboot.domain;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The Rating entity
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rating_id")
    private Integer id;

    @Column(length = 125)
    private String moodysRating;

    @Column(name = "sand_p_rating", length = 125)
    private String sandPRating;

    @Column(length = 125)
    private String fitchRating;

    @Column(columnDefinition = "TINYINT")
    private Integer orderNumber;

}
