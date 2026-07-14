package com.library.bff.dto;

import java.util.UUID;

public record PenaltyRequest(
    UUID loanId,
    String username,
    Double amount
) {}
