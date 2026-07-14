package com.library.msloan.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record LoanRequest(
    UUID bookCopyId,
        String username
) {}
