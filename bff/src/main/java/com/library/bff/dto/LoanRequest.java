package com.library.bff.dto;

import java.util.UUID;

public record LoanRequest(
    UUID bookCopyId,
    String username
) {}
