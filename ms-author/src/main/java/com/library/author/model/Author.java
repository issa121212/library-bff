package com.library.author.model;

import jakarta.persistence.*;
import lombok.*;

@Entity 
@Table(name = "author")
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder

public class Author {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) 
    private String name;

    @Column(nullable = false) 
    private String nationality;

    @Column(name = "birth_year", nullable = false) 
    private Integer birthYear;
}
