package com.library.msloan.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "loan")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Loan {

    @Id
    private UUID id;

    @Column(name = "book_copy_id", nullable = false)
    private UUID bookCopyId;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "loan_date", nullable = false)
    private java.time.LocalDateTime loanDate;

    @Column(name = "due_date", nullable = false)
    private java.time.LocalDateTime dueDate;

    @Column(name = "return_date", nullable = true)
    private java.time.LocalDateTime returnDate;

    @Column(name = "status", nullable = false)
    private String status;
}
