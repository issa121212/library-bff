package com.library.mspenalty.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record PenaltyRequest(
    UUID loanId,
        String username,
        Double amount
) {}
