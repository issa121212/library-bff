package com.library.bff.dto;

import java.util.UUID;

public record ReviewRequest(
    UUID bookId,
    String username,
    Integer rating,
    String comment
) {}
