package com.nnk.springboot.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ratingId;

    @Column(length = 125)
    private String moodysRating;

    @Column(name = "sand_p_rating", length = 125)
    private String sandPRating;

    @Column(length = 125)
    private String fitchRating;

    @Column(columnDefinition = "TINYINT")
    private int orderNumber;

}
