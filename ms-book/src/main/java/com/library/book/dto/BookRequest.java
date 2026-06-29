package com.library.book.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookRequest(
    @NotBlank String title,
    @NotNull  Long   authorId,
    @NotBlank String category,
    @NotBlank String isbn
) {}
