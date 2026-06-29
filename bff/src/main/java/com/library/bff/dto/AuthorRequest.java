package com.library.bff.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AuthorRequest(
    @NotBlank String  name,
    @NotBlank String  nationality,
    @NotNull  Integer birthYear
) {}
