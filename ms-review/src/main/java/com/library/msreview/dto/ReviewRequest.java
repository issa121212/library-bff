package com.library.msreview.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record ReviewRequest(
    UUID bookId,
        String username,
        Integer rating,
        String comment
) {}
