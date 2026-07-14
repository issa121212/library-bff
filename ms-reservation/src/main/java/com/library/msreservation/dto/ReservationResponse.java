package com.library.msreservation.dto;

import java.util.UUID;

public record ReservationResponse(
    UUID id,
        UUID bookId,
        String username,
        java.time.LocalDateTime reservationDate,
        String status
) {}
