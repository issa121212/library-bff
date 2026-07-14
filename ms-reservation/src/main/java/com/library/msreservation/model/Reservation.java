package com.library.msreservation.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "reservation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {

    @Id
    private UUID id;

    @Column(name = "book_id", nullable = false)
    private UUID bookId;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "reservation_date", nullable = false)
    private java.time.LocalDateTime reservationDate;

    @Column(name = "status", nullable = false)
    private String status;
}
