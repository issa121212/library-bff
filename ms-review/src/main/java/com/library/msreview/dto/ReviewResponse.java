package com.library.msreview.dto;

import java.util.UUID;

public record ReviewResponse(
    UUID id,
        UUID bookId,
        String username,
        Integer rating,
        String comment
) {}
