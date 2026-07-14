package com.library.msreservation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record ReservationRequest(
    UUID bookId,
        String username
) {}
