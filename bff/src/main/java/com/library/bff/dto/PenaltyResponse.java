package com.library.bff.dto;

import java.util.UUID;

public record PenaltyResponse(
    UUID id,
    UUID loanId,
    String username,
    Double amount,
    String status
) {}
