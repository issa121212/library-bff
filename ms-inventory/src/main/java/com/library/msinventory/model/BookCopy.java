package com.library.msinventory.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "book_copy")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookCopy {

    @Id
    private UUID id;

    @Column(name = "book_id", nullable = false)
    private UUID bookId;

    @Column(name = "barcode", nullable = false)
    private String barcode;

    @Column(name = "physical_status", nullable = false)
    private String physicalStatus;

    @Column(name = "is_available", nullable = false)
    private Boolean isAvailable;
}
