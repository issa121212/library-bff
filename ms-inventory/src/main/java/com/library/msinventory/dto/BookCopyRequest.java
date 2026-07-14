package com.library.msinventory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record BookCopyRequest(
    UUID bookId,
        String barcode,
        String physicalStatus
) {}
