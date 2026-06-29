package com.library.book.model;

import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;

@Entity 
@Table(name = "book")
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder
public class Book {
    @Id 
    @GeneratedValue(strategy = GenerationType.UUID)
    
    @Column(nullable = false, unique = true)
    private UUID id;

    @Column(nullable = false)                 
    private String title;

    @Column(name = "author_id", nullable = false) 
    private Long authorId;

    @Column(nullable = false)                  
    private String category;

    @Column(nullable = false, unique = true)   
    private String isbn;
}
