package com.library.bff.dto;

import java.util.UUID;

public record ReviewResponse(
    UUID id,
    UUID bookId,
    String username,
    Integer rating,
    String comment
) {}
