package com.library.bff.dto;

import java.util.UUID;

public record LoanResponse(
    UUID id,
    UUID bookCopyId,
    String username,
    java.time.LocalDateTime loanDate,
    java.time.LocalDateTime dueDate,
    java.time.LocalDateTime returnDate,
    String status
) {}
