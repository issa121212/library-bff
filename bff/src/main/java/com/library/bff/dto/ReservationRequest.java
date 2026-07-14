package com.library.bff.dto;

import java.util.UUID;

public record ReservationRequest(
    UUID bookId,
    String username
) {}
